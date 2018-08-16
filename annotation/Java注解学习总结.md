## 关于Java注解学习总结 ##

### 什么是注解 ###

> 注解是 Java 5 的一个新特性。注解是插入你代码中的一种注释或者说是一种元数据（meta data）。这些注解信息可以在编译期使用预编译工具进行处理（pre-compiler tools），也可以在运行期使用 Java 反射机制进行处理。

那么通俗来说，注解就是我们所说的标签，我们可以把一些类，方法，变量，参数添加注解，即加上标签来表明这些是干啥的，以便有需要的时候查看。

### 注解的声明 ###
使用`@interface`声明：


    @Target(ElementType.METHOD)

	@Retention(RetentionPolicy.RUNTIME)		

	public @interface Test {
	}


- 使用`@Target`注解传入`ElementType.METHOD`参数来标明`@Test`只能用于方法上。

- 使用`@Retention(RetentionPolicy.TRUNTIME)`来表示该注解生存期是运行时。

- `@Target`和`@Retention`是由Java提供的元注解，即可以标记自定义注解的注解。

### 元注解 ###

#### `@Target` ####

`@Target`用来约束注解可以应用的地方（如类、方法、方法参数、字段），`ElementType`是枚举类型，其定义如下：

    
    public enum ElementType {
    /**标明该注解可以用于类、接口（包括注解类型）或enum声明*/
    TYPE,

    /** 标明该注解可以用于字段(域)声明，包括enum实例 */
    FIELD,

    /** 标明该注解可以用于方法声明 */
    METHOD,

    /** 标明该注解可以用于参数声明 */
    PARAMETER,

    /** 标明注解可以用于构造函数声明 */
    CONSTRUCTOR,

    /** 标明注解可以用于局部变量声明 */
    LOCAL_VARIABLE,

    /** 标明注解可以用于注解声明(应用于另一个注解上)*/
    ANNOTATION_TYPE,

    /** 标明注解可以用于包声明 */
    PACKAGE,

    /**
     * 标明注解可以用于类型参数声明（1.8新加入）
     * @since 1.8
     */
    TYPE_PARAMETER,

    /**
     * 类型使用声明（1.8新加入)
     * @since 1.8
     */
    TYPE_USE
	}

当注解未指定`Target`值时，则此注解可以用于任何元素上，多个值使用{}包含并用逗号隔开，如下：

    @Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE,
	 METHOD, PACKAGE, PARAMETER, TYPE})


#### @Retention ####
`@Retention`用来约束注解的生命周期，有三个级别:源码级别（SOURCE）、类文件级别（CLASS）、运行时级别（RUNTIME）,

- SOURCE: 注解将被编译器丢弃（该类型的注解信息只会保留在源码里，源码经过编译后，注解信息会被丢弃，不会保留在编译好的class文件里）

- CLASS：注解在class文件中可用，但会被VM丢弃（该类型的注解信息会保留在源码里和class文件里，在执行的时候，不会加载到虚拟机中），请注意，当注解未定义Retention时，默认值是CLASS，如Java内置注解，@Override、@Deprecated、@SuppressWarnning等


- RUNTIME：注解信息将在运行期(JVM)也保留，因此可以通过反射机制读取注解的信息（源码、class文件和执行的时候都有注解的信息），如SpringMvc中的@Controller、@Autowired、@RequestMapping等。

#### @Inherited ####
@Inherited 可以让注解被继承，但这并不是真的继承，只是通过使用@Inherited，可以让子类Class对象使用getAnnotations()获取父类被@Inherited修饰的注解。

	/** 使用@Inherited注解会被子类所继承 */
    @Retention(Retention.RUNTIME)
	@Inherited
	public @interface InheritedTest {
		String value();
	}
	
	/** 未声明@Inherited， 不会被子类继承*/
    @Retention(RetentionPolicy.RUNTIME)
	public @interface NoInheritedTest {
		String value();
	}

	/**父类*/
	
	@InheritedTest("InheritedTest：使用@Inherited的class")
	@NoInheritedTest("NoInheritedTest：未使用@Inherited的class")
	public class Parent {
 
    @InheritedTest("InheritedTest：使用@Inherited method")
    @NoInheritedTest("NoInheritedTest：未使用@Inherited method")
	public void method(){
		
	}
    @InheritedTest("InheritedTest：使用@Inherited method2")
    @NoInheritedTest("NoInheritedTest：未使用@Inherited method2")
	public void method2(){
		
	}
	
    @InheritedTest("InheritedTest：使用@Inherited field")
    @NoInheritedTest("NoInheritedTest：未使用@Inherited field")
	public String a;
	}	

	/**子类  只继承了一个method方法 */
	public class Child extends Parent {
 
	@Override
	public void method() {
		}
	}


	/**使用反射进行测试 */
	public class Test {
    public static void main(String[] args) throws NoSuchMethodException, NoSuchFieldException {
        Class<Child> childClass = Child.class;
        //对类测试
        System.out.println("-------------------------------------");
        System.out.println("对类测试");
        if (childClass.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(childClass.getAnnotation(InheritedTest.class).value());
        }

        if (childClass.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(childClass.getAnnotation(NoInheritedTest.class).value());
        }

        //对方法1测试
        System.out.println("-------------------------------------");
        System.out.println("对方法1测试");
        Method method = childClass.getMethod("method", null);
        if (method.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(method.getAnnotation(InheritedTest.class).value());
        }

        if (method.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(method.getAnnotation(NoInheritedTest.class).value());
        }

        //对方法2测试
        System.out.println("-------------------------------------");
        System.out.println("对方法2测试");
        Method method2 = childClass.getMethod("method2");
        if (method2.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(method2.getAnnotation(InheritedTest.class).value());
        }

        if (method2.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(method2.getAnnotation(NoInheritedTest.class).value());
        }

        //对变量测试
        System.out.println("-------------------------------------");
        System.out.println("对变量测试");
        Field field = childClass.getField("a");
        if (field.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(field.getAnnotation(InheritedTest.class).value());
        }

        if (field.isAnnotationPresent(NoInheritedTest.class)) {
            System.out.println(field.getAnnotation(NoInheritedTest.class).value());
        }
    }
	}

	/**输出结果*/
	-------------------------------------
	对类测试
	InheritedTest：使用@Inherited的class
	-------------------------------------
	对方法1测试
	-------------------------------------
	对方法2测试
	InheritedTest：使用@Inherited method2
	NoInheritedTest：未使用@Inherited method2
	-------------------------------------
	对变量测试
	InheritedTest：使用@Inherited field
	NoInheritedTest：未使用@Inherited field


	我们可以得出结论，使用`@Inherited`注解类的时候，子类继承了父类的注解，但是第一个方法没有继承到注解，说明`@Inherited`对方法无效，但方法2和域拿到的是父类的注解，所以有输出。


#### @Repeatable ####
在Java SE 8中引入的 @Repeatable 注解表明标记的注解可以多次应用于相同的声明或类型使用。 
可以这么理解：有个人可以音乐家，画家，程序员，那我们就用@Repeatable给这个人贴上这三个标签。

	@Retention(RetentionPolicy.RUNTIME)
    public @interface Persons {
		Person[] value();	
	}

	@Repeatable(Persons.class)
	public @interface Person {
		String role default "";
	}

	@Person(role="Painter")
	@Person(role="Musician")
	@Person(role="Lion")
	public class KUN {}

#### 注解的成员变量及其数据类型 ####

我们定义注解，也可以定义一些成员变量，而且还可以用default指定默认值。

	@Retention(RetentionPolicy.RUNTIME)
	public @interface FavouriteAnnotation {

    int num() default 0;

    String name() default "X JAPAN";

	}
	
	/** 默认第0位最喜爱的是X JAPAN*/
	@FavouriteAnnotation()
	public class KUN {}

	/** 指定第1位最喜爱的是Queen*/
	@FavouriteAnnotation(num=1, name="Queen")
	public class KUN {}


注解支持的数据类型有：

- 所有基本类型（int,float,boolean,byte,double,char,long,short）

- String

- Class

- enum

- Annotation

- 上述类型的数组


#### 注解与反射 ####

注解通过反射获取，使用Class对象的isAnnotationPresent()方法判断是否应用了某个注解,如果指定类型的注解存在于此元素上，则返回 true，否则返回 false。
`public boolean isAnnotationPresent(Class<? extend Annotation> annotationClass)`

使用getAnnotation()方法获取Annotation对象。
`public <A extebds Annotation> A getAnnotation(Class<A> annotationClass) {}` 

使用getAnnotations() 获取此元素上存在的所有注解，包括从父类继承的。
`public Annotation[] getAnnotations() {}`

使用getDeclaredAnnotations(),返回直接存在于此元素上的所有注解，注意，不包括父类的注解，调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响，没有则返回长度为0的数组

`public native Annotation[] getDeclaredAnnotations()`

#### 注解的应用场景 ####
> 注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分。注解对于代码的运行效果没有直接影响。
> 
> 注解有许多用处，主要如下： 

> - 提供信息给编译器： 编译器可以利用注解来探测错误和警告信息 
- 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。 
- 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取




