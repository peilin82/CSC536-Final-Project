import java.lang.reflect.Modifier;
import java.lang.reflect.*;

public class Utilities {
    //创建对应的运行时类的对象
    public static Object createInstance(Class c) throws Exception {
        //方法一：调用Class的newInstance方法创建运行时类的对象
        System.out.println(" ");
        System.out.println("createInstance()");
        Object o = c.newInstance();
        System.out.println("方法一：调用Class的newInstance方法创建运行时类的对象  --person: ");
        return  o;
    }
    public static Object createInstance1(Class c) throws Exception {
        //方法二：调用指定的构造器创建运行时类的对象
        //我们指定public类型的构造方法Person(String name,int age,int id)来创建对象
        Constructor constructor = c.getDeclaredConstructor(String.class, int.class, int.class);
        Object o = constructor.newInstance("xxx", 10, 1);
        System.out.println("方法二：调用指定的构造器(public)创建运行时类的对象 --person: " + o.toString());
        return o;
    }
    public static Object createInstance2(Class c) throws Exception {
        //我们指定private类型的构造方法Person(String name)来创建对象
        Constructor constructor = c.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        Object o = (Object) constructor.newInstance("****");
        System.out.println("方法二：调用指定的构造器(private)创建运行时类的对象 --person: "+o.toString());
        return o;
    }
    public String getClassName(Object o){
        return o.getClass().getName();
    }

    public void Reflection(Object classobj){
        String parentName = classobj.getClass().getSuperclass().getName();
        String modifier = Modifier.toString(classobj.getClass().getModifiers());
        Class [] interfaces = classobj.getClass().getInterfaces();
        StringBuffer interfacesBuf = new StringBuffer();
        if(interfaces.length != 0){
            interfacesBuf.append("implements ");
            for(int i = 0; i <= interfaces.length - 1; i++){
                if(i != interfaces.length - 1){
                    interfacesBuf.append(interfaces[i].getName() + ",");
                }else{
                    interfacesBuf.append(interfaces[i].getName());
                }
            }
        }
        String Header = modifier + " class " + classobj.getClass().getName() + " extends " + parentName + interfacesBuf.toString() + "{";
        System.out.println(Header + "\n");

        printFields(classobj.getClass());
        printMethods(classobj.getClass());
        printConstructors(classobj.getClass());

        System.out.println("}");
    }

    public static void printFields(Class<?> classobj){
        System.out.println("    //Fileds");
        String modifier = "";
        String type = "";
        String name = "";
        Field [] fields = classobj.getDeclaredFields();
        for(int i = 0; i <= fields.length - 1; i++){
            modifier = Modifier.toString(fields[i].getModifiers());
            type = fields[i].getType().getName();
            name = fields[i].getName();
            System.out.println("    " + modifier + " " + type + " " + name + ";");
        }
        System.out.println();
    }

    public static void printMethods(Class<?> classobj){
        System.out.println("    //Methods");
        String modifier = "";
        String returnType = "";
        String name = "";
        Class [] paraClasses = null;
        StringBuffer paraType = new StringBuffer();
        Method [] methods = classobj.getDeclaredMethods();
        for(int i = 0; i <= methods.length - 1; i++){
            modifier = Modifier.toString(methods[i].getModifiers());
            returnType = methods[i].getReturnType().getName();
            name = methods[i].getName();
            paraClasses = methods[i].getParameterTypes();
            for(int j = 0; j <= paraClasses.length - 1; j++){
                if(j != paraClasses.length - 1){
                    paraType.append(paraClasses[j].getName() + ", ");
                }
                else{
                    paraType.append(paraClasses[j].getName());
                }
            }
            System.out.println("    " + modifier + " " + returnType + " " + name + "(" + paraType.toString() + ")");
        }
        System.out.println();
    }

    public static void printConstructors(Class<?> classobj){
        System.out.println("    //Constructors");
        Constructor [] constructors = classobj.getConstructors();
        String modifier = "";
        String name = "";
        Class [] paraClasses = null;
        StringBuffer paraType = new StringBuffer();
        for(int i = 0; i <= constructors.length - 1; i++){
            modifier = Modifier.toString(constructors[i].getModifiers());
            name = constructors[i].getName();
            paraClasses = constructors[i].getParameterTypes();
            for(int j = 0; j <= paraClasses.length - 1; j++){
                if(j != paraClasses.length - 1){
                    paraType.append(paraClasses[j].getName() + ", ");
                }
                else{
                    paraType.append(paraClasses[j].getName());
                }
            }
            System.out.println("    " + modifier + " " + name + "(" + paraType.toString() + ")");
        }
        System.out.println();
    }

}
