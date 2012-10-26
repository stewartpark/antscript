Antscript
=========

Tiny scripting language for Java

## License
BSD license

## AntScript snipets
### Importing jar files in Antscript.
 - Script

        #!/usr/bin/jar -jar ants.jar
        
        # import jar file.
        @import('testlib.jar', 'com.ants.testlib.main');
        
        # function `test` is imported from testlib.jar.
        @test('User parameter from antscript.'); 

 - Java (testlib.jar)

        package com.ants.testlib;
        
        public class main {
          public Object test(Object mesg) {
        		System.out.println("Jar loader successfully worked. param:" + mesg.toString());
        		return new Long(0);
        	}
        } 

### Basic expression (arithmetic, logical)

      #!/usr/bin/jar -jar ants.jar
      
      a = 1+2;
      b = 3 * (a / 3) - 2;
      c = b = 0;

### Basic control flow (while, if-else)

      #!/usr/bin/jar -jar ants.jar
      
      force = @input();
      if not @is_rooted() and force = 'Y' {
        @process();
      } else if @is_rooted(){
        @process();
      } else {
        wait = 5;
        while wait {
          if wait >= 1 {
             @sleep(1000);
             continue;
          }
          @retry_root();
        }
      }

### Directly usable interface from other Java project.
   
    import com.nerds.*;

    public class OtherJavaProject { 
      public static void main(String[] args){
           AntScriptCore core = new AntScriptCore();
           // add new function which just returns 321L.
           core.addFunction("test_function", new AntScriptFunction() {
              public Object body(Object[] args) {
                  return new Long(321);
              }
           });
           // add variable
           core.addVariable("test_var", new Long(123));
           core.run("@print(test_var); "); // This will print 123.
           core.runFile("scriptFile.ant"); // Load Antscript file.
       }
    }