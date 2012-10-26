package com.nerds;
import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AntScriptDefaultRuntime {
	static public void setUp(AntScriptCore ar) {
		ar.addFunction("import", new AntScriptFunction (ar) {
			public Object body(Object[] args) {
				this.core.loadJar(args[0].toString(), args[1].toString());
				return new Long(0);
			}
		});
		ar.addFunction("print", new AntScriptFunction() {
			public Object body(Object[] args){
				System.out.println(args[0].toString());
				return new Long(0);
			}
		}); 
		ar.addFunction("input", new AntScriptFunction(ar) {
			private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			public Object body(Object[] args){
				try {
					return (Object)in.readLine();
				} catch (IOException e) {
					this.core.setErrorCode(AntScriptCore.ERR_IO);
					return (Boolean)false;
				}
			}
		}); 
		ar.addFunction("error", new AntScriptFunction() {
			public Object body(Object[] args) {
				System.err.println(args[0].toString());
				//ar.errorCode = 1;
				return new Long(0);
			}
		});
		ar.addFunction("match", new AntScriptFunction(){
			public Object body(Object[] args) {
				return (Boolean)(args[1].toString().matches(args[0].toString()));
			}
		});
		ar.addFunction("replace", new AntScriptFunction(){
			public Object body(Object[] args) {
				return (String)(args[2].toString().replaceAll(args[0].toString(), args[1].toString()));
			}
		});
		ar.addFunction("find", new AntScriptFunction(){
			public Object body(Object[] args) {
				Pattern p = Pattern.compile(args[0].toString());
				Matcher m = p.matcher(args[1].toString());
				if(m.find())  {
					return m.group(0);
				} else {
					return (Boolean)false;
				}
			}
		});
		ar.addFunction("file_read", new AntScriptFunction(ar){
			public Object body(Object[] args) {
				String source = "";
				try {
					String tmp;
					FileInputStream fis = new FileInputStream(args[0].toString());

					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					while((tmp = br.readLine()) != null){
						source += tmp + '\n';
					}
					
				} catch(Exception e) {
					this.core.setErrorCode(AntScriptCore.ERR_IO);
					return (Boolean)false;
				}
				return (Object)source;
			}
		});
		ar.addFunction("file_write", new AntScriptFunction(ar){
			public Object body(Object[] args) {
				try {
					FileWriter fstream = new FileWriter(args[0].toString());
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(args[1].toString());
					out.close();
				} catch(Exception e) {
					this.core.setErrorCode(AntScriptCore.ERR_IO);
					return (Boolean)false;
				}
				return (Boolean)true;
			}
		});
		ar.addFunction("sleep", new AntScriptFunction (ar) {
			public Object body(Object[] args) {
				try {
					Thread.sleep((Long)args[0]);
				} catch (InterruptedException e) {
					this.core.setErrorCode(AntScriptCore.ERR_IO);
				}
				return new Long(0);
			}
		});
		ar.addFunction("int", new AntScriptFunction (ar) {
			public Object body(Object[] args) {
				return new Long(args[0].toString());
			}
		});
		ar.addFunction("str", new AntScriptFunction (ar) {
			public Object body(Object[] args) {
				return args[0].toString();
			}
		});
	}
}

public class AntScriptMain {
	public static void main(String[] args) {
		AntScriptCore core = new AntScriptCore();
		AntScriptDefaultRuntime.setUp(core);

		if(args.length > 0) {
			for(int i = 0;i < args.length; i++){
				try {
					core.runFile(args[i]);
				} catch (AntScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.err.println("Usage: java -jar ants.jar [script]");
		}
	}
}
