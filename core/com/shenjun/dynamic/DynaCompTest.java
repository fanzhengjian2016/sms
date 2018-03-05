package com.shenjun.dynamic;

public class DynaCompTest {
    public static void main(String[] args) throws Exception {
        String fullName = "A";
        StringBuilder src = new StringBuilder();
        src.append("public class A {\n");
        src.append("    public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");

        System.out.println(src);
        
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance =  de.javaCodeToObject(fullName,src.toString());
        System.out.println(instance);
    }
}
