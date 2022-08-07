# Java基础知识
## 第3章 Java的基本程序设计结构
### 基本数据类型
在Java中，共有8种基本数据类型，其中有4种整型，2种浮点型，1种字符类型，1种布尔类型。
4种整型为`byte`、`short`、`int`和`long`，分别在内存中占用1、2、4、8个字节，对应的封装类为`Byte`、`Short`、`Integer`和`Long`。
2种浮点型为`float`和`double`，分别在内存中占用4、8个字节，对应的封装类为`Float`和`Double`。
1种字符类型为`char`，因为在Java中使用Unicode编码表来编码字符，所以`char`类型在内存中占用2个字节，对应的封装类为`Character`。
1种布尔类型为`boolean`，在内存中占用1位，即1/8个字节，对应的封装类为`Boolean`。
#### 基本数据类型和包装类的区别
基本数据类型是基于值，它的值存储于方法栈帧的局部变量表中；包装类是基于对象的，它引用的对象实例存储Java堆中。
#### 自动装箱与拆箱
是JDK1.5的新特性，可以让基本数据类型和包装类无缝地连接使用，编译器在编译时将相关的代码转化成了对应的包装和还原方法，如包装方法`Integer.valueOf()`和还原方法`Integer.intValue()`。
#### 包装类缓存
`Byte`、`Short`、`Integer`、`Long`和`Character`都有缓存，四个整型包装类缓存-128到127共256个数，`Character`缓存128个字符。
当使用包装类的`valueOf()`方法时，如果参数的值在缓存范围内，则返回缓存，否则返回一个新的包装类对象。
使用缓存可以对常用数值的包装类实例对象，不必进行反复的创建与销毁，从而提高GC效率。
### Unicode编码表
Unicode为每种语言中的每个字符规定了统一并且唯一的二进制编码，使得计算机可以用更为简单的方式来呈现和处理字符。Unicode编码系统可分为编码方式和实现方式两个层次。
代码点（code point）是指一个编码表中与一个字符对应的代码值。在Unicode标准中，代码点采用十六进制书写，并加上前缀U+，例如U+0041就是字母A的代码点。
代码单元（code unit），指字符编码的一个基本单元，在Java中每个字符用16位表示。辅助字符采用一对连续的代码单元进行编码。这样构成的编码值一定落入基本的多语言级别中空闲的2048个字节内，通常被称为替代区域（surrogate area）[U+D800U+DFFF用于第二个代码单元]。例如，对于整数集合的数学符号𝕫，它的代码点是U+1D56B，并且是用两个代码单元U+D835和U+DD6B编码的，`String.codePointCount()`代码点数量为1，`String.length()`代码单元数量为2。
编码方式：Unicode使用16位的编码空间，也就是每个字符占用2个字节。这样理论上一共最多可以表示2^16（即65536）个字符，基本满足各种语言的使用。
实现方式：一个字符的Unicode编码是确定的，但是在实际传输过程中，由于不同系统平台的设计不一定一致，以及出于节省空间的目的，对Unicode编码的实现方式有所不同。
#### UTF-8和UTF-16
UTF-8和UTF-16都是Unicode编码表中的一种实现方式。
UTF-8的特点是对不同范围的字符使用不同字节个数的编码。对于英文字符仅使用一个字节即可表示，所以常作为英文字符较多的文件的编码格式。
UTF-16的特点是对所有字符都使用两个字节进行编码。因为使用固定长度的编码，所以UTF-16的编码效率比UTF-8更高效。
### 字符串String
Java字符串就是Unicode字符序列，在内存中使用UTF-16编码格式进行存储。
#### String对象何时放入常量池
[资料1](http://blog.csdn.net/chen1280436393/article/details/51768609)
在代码中使用双引号直接声明的字符串对象，存储于运行时常量池中。使用new创建的字符串对象，存储Java堆中。运行时期字符串的+连接符相当于new，但是在编译时期，被+连接的两个使用双引号声明的字符串对象会被编译器优化，直接生成连接后的字符串存储于运行时常量池中。
`String.intern()`是一个Native方法，它的作用是：如果运行时常量池中已经包含一个等于该String对象内容的字符串，则返回常量池中该字符串的引用；如果没有，则在常量池中创建与此String内容相同的字符串，并返回常量池中创建的字符串的引用。
在JDK1.7中，当常量池中没有该字符串时，intern()方法的实现不再是在常量池中创建与此String内容相同的字符串，而改为在常量池中记录Java堆中首次出现的该字符串的引用，并返回该引用。
_content_copy_
```
String str = new StringBuilder("Test1").append("Test2").toString();
System.out.println(str.intern() == str);
// JDK1.6 : false
// JDK1.7 : true
```
#### String、StringBuffer和StringBuilder的区别
三者都是Java中描述字符串的类，内部都使用char数组来存储字符序列。
`String`类是不可变类，任何对`String`对象修改的行为，如`replace()`、`subString()`方法，都会产生新的对象。
`StringBuffer`和`StringBuilder`则是用来构建字符串的类。`StringBuffer`是线程安全的，其中大部分的方法都是被`synchronized`关键字修饰的。而`StringBuilder`是非线程安全的，所以性能要比`StringBuffer`类好一些，但不适合在多线程情况下使用。
### final、finally和finalize的区别
`final`是用来修饰属性、方法或类的关键字，分别表示属性为常量，方法不可被覆盖，类不可被继承。
`finally`是异常处理语句结构的一部分，表示`finally`中的语句块在异常处理中除非JVM关闭，否则总是执行，可以将释放外部资源的代码写在`finally`块中。
`finalize()`是Object类定义的方法，Java中允许使用`finalize()`方法在垃圾收集器将对象从内存中清除之前做必要的清理工作。这个方法是由垃圾收集器在销毁对象时调用的，通过覆盖`finalize()`方法可以整理系统资源或者执行其他清理工作。
### 运算符
运算符优先等级

1. 从左到右：`()`、`[]`、`.`
1. 从右到左：`!`、`+`（正）、`-`（负）、`~`、`++`、`--`
1. 从左到右：`*`、`/`、`%`
1. 从左到右：`+`（加）、`-`（减）
1. 从左到右：`<<`、`>>`、`>>>`
1. 从左到右：`<`、`<=`、`>`、`>=`、`instanceof`
1. 从左到右：`==`、`!=`
1. 从左到右：`&`（位与）
1. 从左到右：`|`
1. 从左到右：`&&`
1. 从左到右：`||`
1. 从右到左：`?:`
1. 从右到左：`=`、`+=`、`-=`、`*=`、`/=`、`&=`、`|=`、`~=`、`<<=`、`>>=`、`>>>=`

单目数学和位移，大小等于位运算，逻辑三目和赋值。
#### Math类
`Math.round(11.5)`的返回值是12，`Math.round(-11.5)`的返回值是-11。四舍五入的原理是在参数上加0.5然后进行下取整。
### ==和equals的区别
`==`对于基本数据类型变量是判断两个值是否相等，对于对象变量是判断两个对象变量是否引用同一对象，即实际存储的内存地址是否相等。
`equals()`调用的是源于`Object`的方法，如果类覆盖了`equals()`方法，可以根据覆盖的方法来判断两个对象是否逻辑相等，如果没有覆盖则是调用`Object.equals()`方法，即`obj == this`，和`==`无区别。
### foreach原理
使用foreach遍历数组，编译时将代码编译为普通for循环一样的指令集来遍历数组。
使用foreach遍历实现`Iterable`的类，编译时将代码编译为使用迭代器的`Iterator.hasNext()`和`Iterator.next()`来遍历该集合对象。
foreach和for相比，二者的性能区别不大。但是对于链表类型的数据结构，推荐使用foreach遍历。
### switch语句
从JDK1.7开始，`switch`支持用`String`做参数，JDK1.5支持`Enum`做参数，其余的还有`byte`，`short`，`int`，`char`共6种。`Long`不可以做`switch`语句的参数。
## 第4章 Java的基本程序设计结构
### 面向对象
面向对象是一种程序设计思想，将对象作为程序的基本单元，把数据和方法封装在对象中，通过对象的方法可以访问或修改对象相关的数据，从而提高软件的重用性、灵活性和扩展性。
面向对象的三个基本特征：

- 封装：把客观事物封装成抽象的类，隐藏类中的属性和实现细节，仅对外公开特定的接口。
   - 抽象：对一类对象总结其共同特征的过程，包括数据抽象和行为抽象两方面。抽象只关注对象有哪些属性和行为，并不关注这些行为的细节是什么。
- 继承：子类具有父类的属性和方法，并可以重新定义或追加属性和方法等。
- 多态：指同一个行为具有多个不同表现形式或形态的能力。具体表现方式有两种，重载和覆盖。
   - 重载，是指允许存在多个同名方法，但这些方法的参数表不同。
   - 覆盖，是指子类重新定义父类方法的行为。
#### 类之间的关系

- 依赖（uses-a）
- 聚合（has-a）
- 继承（is-a）
### this和super的区别
在构造器中，`this`可以调用当前类的其他构造器方法，`super`可以调用父类的构造器方法。当父类含有有参数的构造器时，子类必须在其自己的构造器中调用父类的任一构造器方法。
`this`表示当前对象的引用，`super`则无此概念，但可以使用`super.method()`来调用父类未被覆盖之前的方法。
在其他情况，可以通过`this.mFeild`，`super.mFeild`来区别子类和父类中同名的成员变量。
### 访问权限修饰符
访问权限修饰符一共有四种，分别为`public`、`protect`、默认、`private`。

| 访问权限 | 同一个类 | 同一个包 | 子类 | 不同包 |
| :---: | :---: | :---: | :---: | :---: |
| public | Y | Y | Y | Y |
| protect | Y | Y | Y | N |
| 默认 | Y | Y | N | N |
| private | Y | N | N | N |

### static关键字
#### 用法

1. 静态导入
1. 静态代码块
1. 静态变量、静态常量
1. 静态方法
1. 静态内部类
#### 静态方法是否可以被继承
Java中静态变量和静态方法可以被继承，但是没有被覆盖（overwrite）而是被隐藏。
静态方法和属性是属于类的，调用的时候直接通过类名。方法名完成的，不需继承机制就可以调用如果子类里面定义了静态方法和属性，那么这时候父类的静态方法或属性称之为“隐藏”，你如果想要调用父类的静态方法和属性，直接通过父类名.方法名或变量名完成，至于是否继承一说，子类是有继承静态方法和属性，但是跟实例方法和属性不太一样，存在“隐藏”的这种情况。
### 类初始化时代码执行顺序
不涉及继承关系的情况下（先静态）：

1. 按照出现顺序先后，执行静态成员变量定义与静态初始化块
1. 按照出现顺序先后，执行非静态成员变量定义与非静态初始化块
1. 执行构造函数

涉及继承关系的情况下（先静态后父类再子类）：

1. 执行父类的静态成员变量定义与静态初始化块，执行子类的静态成员变量定义与静态初始化块
1. 执行父类的非静态成员变量定义与非静态初始化块，执行父类构造方法
1. 执行子类的非静态成员变量定义与非静态初始化块，执行子类构造方法
### 重载
重载：一个类中的多个方法具有相同的名字、不同的参数。
重载解析：编译器在编译时，通过变量的个数与静态类型进行匹配挑选出相应的方法。如果编译器找不到匹配的参数，或者找出多个可能的匹配，就会产生编译时错误。
### import原理
import仅作为类全名的补充机制，在编译时，编译器会根据import的包来把类名补充成全名，转化为字节码。之后对类的操作则是虚拟机加载类机制原理。
## 第5章 继承
### Object有哪些公用方法
Object类一共有12个方法，其中9个public方法，2个protected方法，1个private static方法。
public的方法分别是getClass，hashCode，equals，toString，notify，notifyAll，3个wait方法。
protected的方法分别是clone，finalize。
private static的方法为registerNatives。
### 重载（overload）与覆盖（override）
重载是编译时的多态性，发生在一个类中，类中具有若干方法名相同的方法，但是其参数表不同。
覆盖是运行时的多态性，发生在多个类中，子类对父类的方法进行重新定义。
### 静态绑定和动态绑定
[资料1](http://blog.csdn.net/zhangjk1993/article/details/24066085)
绑定：指的是一个方法的调用与方法所在的类（方法主体）关联起来。对于Java来说，绑定分为静态绑定和动态绑定，或者称为前期绑定和后期绑定。
静态绑定：在程序执行前方法已经被绑定，也就是说在编译过程中就已经知道这个方法到底是哪个类中的方法。Java当中的方法只有final，static，private和构造方法是静态绑定。
动态绑定：在运行时根据具体对象的类型进行绑定。
区别：静态绑定发生在编译时期，动态绑定发生在运行时。
### 反射
[资料1](http://www.importnew.com/23902.html)
反射是指计算机程序在运行时（Run time）可以访问、检测和修改它本身状态或行为的一种能力。对于Java而言，允许程序在运行时通过反射API获取任何一个已知名称的class的全部信息，包括constructor、field、method等。
getFields()和getDeclaredFields()的区别，getFields()可以获取该类所有public的字段，包括父类提供的；getDeclaredFields()可以获取该类所有的字段，包括public、protected、private，但是不包括父类中的字段。同样类似的还有getConstructors()和getDeclaredConstructors()，getMethods()和getDeclaredMethods()。
setAccessible(true)可以使非public的field、method和constructor得以访问。
实现原理，源代码经过编译器编译后会生成Java字节码文件，Java虚拟机加载类文件后会生成对应的Class对象，Java中的反射就是通过这个Class对象获取类中的所有信息，比如方法、成员以及构造器等。
总结，Java反射能够使我们的代码更加灵活，例如通过配置文件的不同信息加载不同的class；但是它的缺点就是运用反射会使我们的软件的性能降低(每次调用都会检测方法的访问权限，参数类型是否匹配及装箱拆箱，并且动态代码无法被虚拟机优化等)，复杂度增加。
## 第6章 接口与内部类
### abstract和interface的区别
抽象类和接口都是Java语言中对抽象概念描述的两种机制。
抽象类是用来创建继承层级里子类的模板，只能被用作子类的父类。接口则是抽象方法的集合，用来对类的若干行为进行抽象。
区别：

1. 抽象类使用extends关键字继承，接口使用implements关键字实现
1. 一个类只能继承一个抽象类，但可以实现多个接口。
1. 抽象类内可以有构造器、成员变量，而接口没有。
1. 接口中只能有public的抽象方法和static final的常量。
### 内部类
[资料1](http://blog.csdn.net/zhangjg_blog/article/details/19996629)
内部类又称嵌套类，分为静态内部类和非静态内部类，非静态内部类又分为成员内部类、局部内部类和匿名内部类。编译器将会把内部类翻译成用$分隔外部类名与内部类名的常规类文件。对于非静态内部类，内部类对象总有一个隐式引用，它指向了创建它的外部类对象。
内部类的优点：可以更好的实现类的封装性，非静态内部类拥有外部类的访问权限，减少了命名冲突。

- 静态内部类：没有指向外部类的引用，仅仅是嵌套在外部类里。
- 非静态内部类：拥有指向外部类的引用，类中不能含有静态的成员变量或方法。
   - 成员内部类
   - 局部内部类：同成员内部类相似，但作用域仅在定义的范围内可以使用。
   - 匿名内部类：同成员内部类相似，但是没有属于自己的构造方法。
### 代理
[资料1](http://www.importnew.com/23972.html)
代理设计模式分为静态代理和动态代理。
#### 静态代理
所谓静态也就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。通常情况下，静态代理中的代理类和委托类会实现同一接口或是派生自相同的父类，这就是设计模式中的代理模式。
代理模式（委托模式）和包装模式（装饰者模式）的区别：代理模式关注于控制对对象的访问，而包装模式关注于在一个对象上动态的添加方法。换句话说，用代理模式，代理类（proxy class）可以对它的客户隐藏一个对象的具体信息。因此，当使用代理模式的时候，我们常常在一个代理类中创建一个对象的实例。并且，当我们使用包装模式的时候，我们通常的做法是将原始对象作为一个参数传给装饰者的构造器。
#### 动态代理
动态代理是在实现阶段不用关心代理类，而在在程序运行时创建的代理方式。动态代理的优势在于可以很方便的对代理类的方法进行统一的处理。
使用Proxy.newProxyInstance()生成动态代理类。
实现原理：通过ProxyGenerator生成代理类的字节码，代理类是Proxy的子类，所以只能传递Interface作为参数。当调用接口的指定方法时，其实是调用Proxy子类生成的方法，内部为InvocationHandler.invoke()，从而达到代理的效果。
## 第11章 异常、断言、日志和调试
### Error和Exception
[资料1](http://www.importnew.com/22600.html)
[资料2](http://www.importnew.com/20629.html)
[资料3](http://www.importnew.com/20645.html)
异常情形是指阻止当前方法或者作用域继续执行的问题。也就是说当前的环境下程序无法正常运行下去，这时它所就会从当前环境中跳出，并抛出异常。
Throwable是Java语言中所有错误和异常的超类，其包含了线程创建时线程执行堆栈的快照，提供了printStackTrace()等接口用于获取堆栈跟踪数据等信息。
Throwable有两个子类为Error和Exception。
Error类描述了Java运行时系统的内部错误和资源耗尽错误，属于应用程序无法处理的情况。如果出现了这样的内部错误，除了通告给用户，并尽力使程序安全地终止之外，再也无能为力了。
Exception是程序可以处理的异常，又分为受检异常和非受检异常。其中CheckException发生在编译阶段，必须要使用try catch语句执行代码。而UncheckedException（也叫做Runtime Exception）发生在运行期，主要是由于程序的逻辑问题所引起的。
常见异常：

1. 非受检：NullPointerException，ClassCastException，IndexOutOfBoundsException，IllegalArgumentException
1. 受检：FileNotFoundException，IOException，SQLException
### try catch finally
try语句块中有一个return语句，那么紧跟在这个try语句块后的finally语句块里的代码会不会被执行，什么时候被执行，在return前还是后？
在finally中改变返回值的做法是不好的，因为如果存在finally代码块，try中的return语句不会立马返回调用者，而是记录下返回值待finally代码块执行完毕之后再向调用者返回其值，然后如果在finally中修改了返回值，就会返回修改后的值。
## 第12章 泛型程序设计
### 泛型
[资料1](http://www.importnew.com/24029.html)
泛型是Java1.5的新特性，泛型的本质是参数化类型，也就是指所操作的数据类型被指定为一个参数。这个参数类型类型可以用在类、接口和方法中，分别被称为泛型类、泛型接口、泛型方法。
在Java SE 1.5之前，没有泛型的情况的下，通过对类型Object的引用来实现参数的“任意化”，“任意化”带来的缺点是要做显式的强制类型转换，而这种转换是要求开发者对实际参数类型可以预知的情况下进行的。对于强制类型转换错误的情况，编译器可能不提示错误，在运行的时候才出现异常，这是一个安全隐患。
泛型的好处是在编译的时候检查类型安全，并且所有的强制转换都是自动和隐式的，以提高代码的重用率。
#### 类型擦除
类型擦除就是指Java泛型只能用于在编译期间的静态类型检查，然后编译器生成的代码会擦除相应的类型信息，这样到了运行期间实际上JVM根本就不知道泛型所代表的具体类型。这样做的目的是因为Java泛型是1.5之后才被引入的，为了保持向下的兼容性，所以只能做类型擦除来兼容以前的非泛型代码。
#### 桥接方法
_content_copy_
```
public class Father<T> {
	public T method(T t) {
		return t;
	}
}
public class Child<String> {
	public String method(String str) {
		return str;
	}
}
```
该代码经过编译器类型擦除后，会产生如下代码：
_content_copy_
```
public class Father {
	public Object method(Object obj) {
		return obj;
	}
}
public class Child {
	public Object method(Object obj) {
		return method((String) obj);
	}
	
	public String method(String str) {
		return str;
	}
}
```
Child类中的第一个方法即为桥接方法。
#### 泛型的局限性

1. 不能用基本数据类型实例化类型参数
1. 不能实例化类型变量
1. 不能`instanceof Pair<String>`
1. 不能创建参数化类型数组
1. 不能在静态域或方法中引用泛型类的参数
1. 不能抛出或捕获泛型类的实例
#### 通配符

- ?
- extends
- super
## 第13章 集合
### 集合
[资料1](http://www.cnblogs.com/LittleHann/p/3690187.html)
在Java中，集合框架的继承体系中，最顶层有两个接口：Collection和Map。Collection表示一组单一结构的数据集，而Map表示一组键值对结构的数据集。
Collection结构中主要有三个接口：Set、List和Queue。Set为集合，List为线性表，Queue为队列。
#### Hash Code
散列表是一种根据散列码而直接访问存储位置的数据结构。
散列码（Hash Code）可以提高散列存储结构中，数据的查找速度。在散列数据存储结构中，会把存储区域划分为一个个的bucket，然后根据对象的散列码，来确定对象存储到哪一个bucket中。
Hash Code是用来在散列存储结构中确定对象的存储地址。Object类拥有计算每个对象哈希值的方法，其值为对象的存储地址。
如果两个输入串的hashcode值一样，则称这两个串是一个碰撞（Collision）。处理方法有开放寻址法、再哈希法、链接法等。
#### Map
##### HashMap（Java1.8源码）
HashMap基于散列表原理对Map类型数据结构的具体实现，内部使用链表数组来存储数据，其中每一个链表称之为桶（bucket），可以通过put()和get()方法添加和获取对象。
当添加数据时，通过Key的hashCode来找到对应的桶，若桶中已经存在==或equals()该数据元素的Key值，会根据ifAbsent参数来决定是否更新Value值；若不存在，则在链表尾部创建新的Node，如果该链表的长度超过一个阈值，则会将该链表转化为红黑树结构。
当产生哈希碰撞时，只要Key值不相等，仍然创建新的Node并添加到桶中。
当获取数据时，通过Key的哈希值来找到对应的桶，之后通过红黑树搜索算法或循环遍历桶数据，来查找到==或equals()当前Key的数据元素，并返回。
HashMap的扩容，当已存储的数据超过threshold变量的值，则进行扩容，会创建新的链表数组空间，并将原先的数据根据哈希值重新存放到相应的桶中。因为较多的数据存放在较少的桶中，对数据的存取操作并不是散列原理希望的O(1)时间效率。
Java1.7源码大同小异，只不过没有TreeNode。
##### Hashtable
Hashtable的实现原理和HashMap相同，都是基于散列表原理对Map类型数据结构的具体实现，但内部并没有对每个桶的数据，进行红黑树的优化。并且Hashtable大部分的方法都被synchronized关键字修饰，虽然保证了线程安全性，但是效率降低。
##### TreeMap
TreeMap使用红黑树（自平衡二叉排序树）来存储元素。调用put()方法时，依据Key进行排序，然后放入相应的位置。调用get()方法时，通过二叉排序树查找算法查找数据元素。
##### LinkedHashMap
LinkedHashMap继承自HashMap，所以其具有HashMap相应的特点，并且添加了头尾指针来记录数据元素的插入顺序相同。
LinkedHashMap覆盖了HashMap中操作完结点后的回调方法，如afterNodeAccess()，将数据元素链接到链表中。当调用put()方法后，新加入的元素被追加到尾指针后面，所以能在迭代时保持插入顺序。
##### WeakHashMap
WeakHashMap和HashMap的实现原理大体相同，但WeakHashMap的Entry继承自WeakReference，对Entry的Key实现弱引用。当增删改查数据元素时，会删除掉Key已被回收的数据元素。
##### IdentityHashMap
IdentityHashMap使用Object数组存储键值对，例如i位置存储Key，i+1位置存储对应的Value。IdentityHashMap允许equals相等，但==不为true的多个Key同时存在。
##### EnumMap
EnumMap用于存储Key为枚举类型的键值对，当调用put()方法时，首先检查Key是否为Map定义的枚举类型，然后根据枚举类型的ordinal，将Value存储到一个长度为枚举size的数组中。get()方法流程类似，先检测Key是否为枚举类型，若是则从数组中取出对应的Value。
#### Set
##### HashSet
HashSet内部使用HashMap对象来存储数据元素，将一个Object对象作为所有Key的Value。当调用add()方法时，实际上调用的是HashMap的put()方法。
##### LinkedHashSet
LinkedHashSet继承自HashSet，所有的构造器都调用一个父类的构造器，将HashMap替换为LinkedHashMap，所以能拥有LinkedHashMap的特点，迭代时保证原有的插入顺序。
##### TreeSet
TreeSet与HashSet类似，内部使用一个TreeMap变量来存储元素，所以拥有TreeMap的特点。
##### EnumSet
EnumSet是一个抽象类，使用Enum数组存储元素。
#### List
##### ArrayList
ArrayList是对线性表数据结构的具体实现，内部使用Object数组存储数据元素。
当添加数据时，可以调用两个add()方法和一个addAll()方法。首先对elementData进行检测，如果要添加元素的个数加上已存储的元素个数大于elementData的容量，则对elementData进行扩容，创建新的Object数组，并使用Native的方法`System.arraycopy()`复制原数据。
`ArrayList.add(int index, E element)`和`ArrayList.addAll(Collection<? extends E> c)`方法会使用Native的方法`System.arraycopy()`复制原数据，之后再将数据添加到相应位置。
当删除数据时，可以调用两个remove()方法和clear()方法。
`ArrayList.remove(int index)`方法会使用Native的方法`System.arraycopy()`来将删除位置之后的数据向前复制，并移除末尾数据，防止内存泄漏。
`ArrayList.remove(Object o)`方法会循环遍历数据，找到并移除。
`ArrayList.clear()`方法会循环遍历数据，并将其置为null。
##### LinkedList
LinkedList内部使用双向链表结点存储数据。当调用add()时，对数据生成一个Node对象，链接到链表相应的位置中。当调用remove()方法时，查找到元素对应的Node对象，然后将其从链表中移除。
##### Vector
Vector的内部实现和ArrayList一样，都是使用Object数组存储数据元素，但其中大部分方法被synchronized关键字修饰，虽然保证了线程安全性，但是效率降低。
##### Stack
Stack继承自Vector，存储栈类型的数据结构，增加了push()、pop()、peek()方法。
### 集合对比
#### Collection与Collections的区别
Collection是各种集合结构数据的父接口，继承其的接口主要有Set、List和Queue；Collections是对有关集合操作的专用静态工具类，提供搜索、排序、复制等静态方法。
#### Map，Set，List，Queue，Stack的特点与用法
[资料1](http://blog.csdn.net/u011860731/article/details/48717123)
[资料2](http://blog.csdn.net/u011860731/article/details/48717185)
都是Java中的容器接口，对常用数据结构的抽象。

- Map为键值对数据。
- Set为集合数据。
- List为线性表数据。
- Queue为队列数据。
- Stack为栈数据。
#### ArrayList，LinkedList，Vector的区别
三者都是Java中线性表数据结构的具体实现类。
ArrayList内部使用数组存储数据，即顺序存储结构，其特点为增删慢，查找快。
LinkedList使用双向链表存储数据，即链式存储结构，其特点为增删快，查找慢。而且LinkedList还实现了Deque接口，含有双端队列的方法。
ArrayList和LinkedList是非线程安全的，不适合在多线程情况下使用。
Vector和ArrayList一样使用数组存储数据，但是线程安全的，其中大部分方法被synchronized关键字所修饰，带来的缺点就是性能较慢。
#### HashMap和Hashtable的区别
HashMap和Hashtable都是Map接口具体实现类，主要区别有线程安全性和性能。
HashMap是非线性安全的，put()方法允许key或者value为null，单线程下性能高于Hashtable。
Hashtable是线性安全的，put()方法不允许key或者value为null。
HashMap可以通过`Collections.synchronizeMap(hashMap);`返回一个SynchronizedMap包装类进行同步。
#### HashMap，LinkedHashMap，TreeMap的区别
三者都是Map类型的数据结构的具体实现，即保存键值对数据的类。
HashMap利用Key的哈希值具有很快的访问速度，但遍历时取得的数据是随机的。
LinkedHashMap是HashMap的子类，使用双向链表存储数据，遍历时取得数据是按照插入顺序返回的。
TreeMap使用红黑树来存储数据，能够将它保存的数据根据Key排序，默认是按照Key的升序排序，也可以指定。遍历时取得的数据是排序过的。
#### HashMap和ConcurrentHashMap的区别，HashMap的底层源码
HashMap是非线程安全的，ConcurrentHashMap是线程安全的，为Hashtable的替代类。
ConcurrentHashMap实现线程安全的原理是“分段锁”。把Map分为n(默认是16)个segment，在同一segment的put或remove时，进行加锁。即两个线程访问不同segment中的数据时，不会阻塞；但两个线程访问同一segment中的数据时，会产生阻塞。
HashMap的内部使用链表数组来存储数据，首先根据key得到hash值，然后计算出数组下标，如果多个key对应同一数组下标，就用链表串起来。
## 第14章 多线程
多线程：在操作系统中并发执行多个线程，从而提升整体处理性能。

- 优点：程序的运行速度可能加快。
- 缺点：操作系统在大量线程之间切换会耗费性能；更多的线程会耗费更多的内存；易产生数据并发问题；可能会产生死锁问题。
### 程序、进程和线程

- 程序：是应用软件执行的蓝本，一段静态代码，一组指令的有序集合，是一个静态的实体。
- 进程：是程序在操作系统上的一次执行过程，对应着从代码加载，执行至执行完毕的一个完整的过程，是一个动态的实体。
- 线程：是操作系统能够进行运算调度的最小单位。它被包含在进程之中，是进程中的实际运作单位。

进程与线程的区别：

1. 从属：一个进程可以拥有多个线程，但一个线程同时只能被一个进程所拥有。
1. 分配：进程是资源分配的基本单位，线程是处理机调度的基本单位，所有的线程共享其所属进程的所有资源。
1. 同步：线程之间很容易进行协作，而进程之间需要通过IPC机制进行协作。
1. 划分：线程的划分尺度更小，并发性更高。
1. 公私：线程共享进程的数据的同时，有自己私有的的堆栈。
1. 执行：线程不能单独执行，但是每一个线程都有程序的入口、执行序列以及程序出口。它必须组成进程才能被执行。
### 线程
#### 线程的状态
状态图

1. New（新创建）
1. Runnable（可运行）：一旦调用start方法，线程处于runnable状态。一个可运行的线程是否正在运行取决于操作系统给线程提供运行的时间。
1. Blocked（被阻塞）
1. Waiting（等待）
1. Timed waiting（计时等待）
1. Terminated（被终止）
#### 线程的阻塞
导致线程阻塞的原因主要由以下几方面：

1. 线程执行了Thread.sleep()方法，让出CPU资源。
1. 线程要执行一段同步代码，由于无法获得相关的同步锁，只好进入阻塞状态。
1. 线程执行了一个对象的wait()方法，进入阻塞状态，只有等到其他线程执行了该对象的notify()或者notifyAll()或者指定睡眠时间来唤醒线程。
1. 线程执行I/O操作或进行远程通信时，会因为等待相关的资源而进入阻塞状态。
#### 线程的属性

- 线程优先级（`Thread.setPriority()`）
- 守护线程：唯一用途是为其他线程提供服务。当只剩下守护进程时，虚拟机就退出了。
- 未捕获异常处理器（`Thread.setUncaughtExceptionHandler()`）
#### 实现多线程的两种方法：Thread与Runnable
Thread是类，Runnable是接口
一个Thread对象被创建后，多次调用start()方法，同一时间仅有一个线程被执行。而Runnable接口同时可以被多个线程并发执行。
#### wait()和sleep()的区别
wait()是Object类中的方法，而sleep()则是Thread类中的静态方法。
sleep()使当前线程进入阻塞状态，让出CPU资源，但是不释放对象锁。所以当在一个同步块中调用sleep()方法时，虽然线程休眠，但是对象锁并没有被释放，其他线程无法访问这个对象。在sleep()休眠时间期满后，该线程不一定会立即执行，这是因为其它线程可能正在运行而且没有被调度为放弃执行，除非此线程具有更高的优先级。
当一个线程执行到wait()方法时，它就进入到一个和该对象相关的等待池中，同时暂时释放对象锁。wait()使用notify()或者notifyAll()或者指定睡眠时间来唤醒当前等待池中的线程。wait()必须放在synchronized语句块中，否则会抛出IllegalMonitorStateException异常。
### 同步
多个线程同时对同一个对象实例进行操作，根据各线程访问数据的顺序，可能会产生不同的错误结果。为了避免这种对共享数据的讹误，需要对数据进行同步操作。通常来说，是在访问临界资源的代码前面加上一个锁，当访问完临界资源后释放锁，让其他线程继续访问。在Java中，提供了两种方式来实现同步互斥访问：synchronized和Lock。
在Java中，每一个对象都拥有一个锁标记（monitor），也称为监视器，多线程同时访问某个对象时，线程只有获取了该对象的锁才能访问。
#### synchronized
synchronized可以修饰方法

1. 当一个线程正在访问一个对象的synchronized方法，那么其他线程不能访问该对象的其他synchronized方法。这个原因很简单，因为一个对象只有一把锁，当一个线程获取了该对象的锁之后，其他线程无法获取该对象的锁，所以无法访问该对象的其他synchronized方法。
1. 当一个线程正在访问一个对象的synchronized方法，那么其他线程能访问该对象的非synchronized方法。这个原因很简单，访问非synchronized方法不需要获得该对象的锁，假如一个方法没用synchronized关键字修饰，说明它不会使用到临界资源，那么其他线程是可以访问这个方法的，
1. 如果一个线程A需要访问对象object1的synchronized方法fun1，另外一个线程B需要访问对象object2的synchronized方法fun1，即使object1和object2是同一类型），也不会产生线程安全问题，因为他们访问的是不同的对象，所以不存在互斥问题。

synchronized还可以修饰静态方法，所占用的锁为Class类的锁。
synchronized可以修饰代码块，当执行这段代码块时，线程会获取对象的锁，从而使得其他线程无法同时访问该代码块。
从反编译获得的字节码可以看出，synchronized代码块实际上多了monitorenter和monitorexit两条指令。
对于synchronized方法或者synchronized代码块，当出现异常时，JVM会自动释放当前线程占用的锁，因此不会由于异常导致出现死锁现象。
类锁和对象锁：
_content_copy_
```
class LockTest {
    private Object object = new Object();
    public static synchronized void method1() {
    }
    public void method2() {
        synchronized (LockTest.class) {
        }
    }
    public synchronized void method3() {
    }
    public void method4() {
        synchronized (this) {
        }
    }
    public void method5() {
        synchronized (object) {
        }
    }
}
```
以上代码method1()和method2()为类锁，method3()、method4()、method5()都为对象锁。
#### Lock类
代码被synchronized修饰后，线程无法主动释放获取到的锁，如果该线程因为特殊情况而被阻塞，则其他等待锁释放的线程将持续等待，导致降低程序执行效率。
Lock类确保任何时刻只有一个线程进入临界区。一旦一个线程封锁了锁对象，其他任何线程都无法通过lock语句。当其他线程调用lock时，它们被阻塞，直到第一个线程释放锁对象。
原理为Lock类内部定义了一个QueuedSynchronizer，当调用lock()方法时，会依次将当前线程加入到队列中，而lock()内部则是调用的`Thread.currentThread().interrupt()`。
ReadWriteLock是区分功能的锁。读和写是两种不同的功能，读-读不互斥，读-写互斥，写-写互斥。这样的设计是并发量提高了，又保证了数据安全。

- ReentrantLock：可重入锁
- ReentrantReadWriteLock：可重入读写锁
#### synchronized和Lock比较

1. Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现的。
1. synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁。
1. Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断。
1. 通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
1. Lock可以提高多个线程进行读操作的效率。

在性能上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），此时Lock的性能要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。
#### 可重入锁、可中断锁、公平锁、读写锁
可重入锁，也叫做递归锁，指的是同一线程外层函数获得锁之后，内层递归函数仍然有获取该锁的代码，但不受影响。在Java中ReentrantLock和synchronized都是可重入锁。可重入锁最大的作用是避免死锁。
可中断锁，可以相应中断的锁。在Java中，synchronized就不是可中断锁，而Lock是可中断锁。Lock类中lockInterruptibly()的用法时已经体现了Lock的可中断性。
公平锁，即尽量以请求锁的顺序来获取锁。比如同是有多个线程在等待一个锁，当这个锁被释放时，等待时间最久的线程（最先请求的线程）会获得该所，这种就是公平锁。非公平锁即无法保证锁的获取是按照请求锁的顺序进行的，这样就可能导致某个或者一些线程永远获取不到锁。在Java中，synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁。
读写锁，将一个资源（比如文件）的访问分成了两个锁，一个读锁和一个写锁。多个线程之间的读操作不会发生冲突，但是若有线程持有写锁，则其他线程的读写都会进入阻塞状态。
#### volatile
[资料1](http://www.importnew.com/24082.html)
原子性：一个操作或者多个操作要么全部执行，并且执行的过程不会被任何因素打断，要么就都不执行。在Java中，对基本数据类型的变量的读取和赋值操作是原子性操作。
可见性：是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。在Java中使用volatile关键字来保证可见性，当一个共享变量被volatile修饰时，它会保证修改的值会立即被更新到主存，当有其他线程需要读取时，它会去内存中读取新值。
有序性：即程序执行的顺序按照代码的先后顺序执行。在Java中，处理器为了提高程序运行效率，可能会对输入代码进行优化，它不保证程序中各个语句的执行先后顺序同代码中的顺序一致，但是它会保证程序最终执行结果和代码顺序执行的结果是一致的。在Java里面，可以通过volatile关键字来保证一定的“有序性”。另外可以通过synchronized和Lock来保证有序性，很显然，synchronized和Lock保证每个时刻是有一个线程执行同步代码，相当于是让线程顺序执行同步代码，自然就保证了有序性。
一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：可见性和有序性。

1. 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
1. 当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行。
1. 在进行指令优化时，不能将在对volatile变量的读操作或者写操作的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。

实现原理
1.可见性
处理器为了提高处理速度，不直接和内存进行通讯，而是将系统内存的数据独到内部缓存后再进行操作，但操作完后不知什么时候会写到内存。
如果对声明了volatile变量进行写操作时，JVM会向处理器发送一条Lock前缀的指令，将这个变量所在缓存行的数据写会到系统内存。 这一步确保了如果有其他线程对声明了volatile变量进行修改，则立即更新主内存中数据。
但这时候其他处理器的缓存还是旧的，所以在多处理器环境下，为了保证各个处理器缓存一致，每个处理会通过嗅探在总线上传播的数据来检查自己的缓存是否过期，当处理器发现自己缓存行对应的内存地址被修改了，就会将当前处理器的缓存行设置成无效状态，当处理器要对这个数据进行修改操作时，会强制重新从系统内存把数据读到处理器缓存里。这一步确保了其他线程获得的声明了volatile变量都是从主内存中获取最新的。
2.有序性
Lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成。
### 死锁
[资料1](https://baike.baidu.com/item/%E6%AD%BB%E9%94%81/2196938?fr=aladdin)
死锁是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。
### ThreadLocal
[资料1](http://www.importnew.com/17849.html)
线程本地变量，为每个线程提供独立的变量备份。即每一个线程都可以独立地改变自己的对象备份，而不会影响其它线程所对应的备份。
实现原理：
在Thread类内部有一个ThreadLocal.ThreadLocalMap类型的成员变量threadLocals，这个threadLocals就是用来存储实际的变量副本的，内部为HashMap的实现原理，键值为当前ThreadLocal变量，value为变量副本（即T类型的变量）。
初始时，在Thread里面，threadLocals为空，当通过ThreadLocal变量调用get()方法或者set()方法，就会对Thread类中的threadLocals进行初始化，并且以当前ThreadLocal变量为键值，以ThreadLocal要保存的副本变量为value，存储到threadLocals。
ThreadLocal关键方法：

- get()
- set()
- remove()
- initialValue()

ThreadLocal常见使用场景为数据库连接等。
### 并发包
[资料1](http://www.importnew.com/24594.html)
[资料2](http://www.importnew.com/21288.html)
Collection包结构中的集合当迭代时被其他线程所修改，会抛出ConcurrentModificationException，所以在Java1.5中提供了线程安全集合。
#### 原子类
[资料1](https://www.cnblogs.com/chengxiao/p/6789109.html)
原子类使用了CAS指令来保证了对实例对象操作的原子性，而不是使用锁。例如AtomicInteger提供了incrementAndGet()和decrementAndGet()，分别以原子方式将一个整数自增或自减。
#### 阻塞队列
阻塞队列（BlockingQueue）提供了线程安全的队列访问方式：当阻塞队列进行插入数据时，如果队列已满，线程将会阻塞等待直到队列非满；从阻塞队列取数据时，如果队列已空，线程将会阻塞等待直到队列非空。并发包下很多高级同步类的实现都是基于BlockingQueue实现的。
BlockingQueue接口提供添加和删除元素的方法。

- add：添加元素到队列里，添加成功返回true，由于容量满了添加失败会抛出IllegalStateException异常。
- offer：添加元素到队列里，添加成功返回true，添加失败返回false。
- put：添加元素到队列里，如果容量满了会阻塞直到容量不满。
- poll：删除队列头部元素，如果队列为空，返回null。否则返回元素。
- remove：基于对象找到对应的元素，并删除。删除成功返回true，否则返回false。
- take：删除队列头部元素，如果队列为空，一直阻塞到队列有元素并删除。

ArrayBlockingQueue的原理是使用一个可重入锁和这个锁生成的两个条件对象进行并发控制，分别为notFull和notEmpty。ArrayBlockingQueue是一个带有长度的阻塞队列，初始化的时候必须要指定队列长度，且指定长度之后不允许进行修改，内部使用Object数组存储数据元素。
LinkedBlockingQueue的原理是使用两个可重入锁takeLock和putLock，分别对添加和删除元素操作进行加锁。内部使用单向链表存储数据元素，容量大小如果不初始化的话，默认为Integer.MAX_VALUE。
PriorityBlockingQueue是带优先级的无界阻塞队列，每次出队都返回优先级最高的元素。
ArrayBlockingQueue中添加数据阻塞的时候，需要消费数据才能唤醒。而LinkedBlockingQueue中放入数据阻塞的时候，因为它内部有2个锁，可以并行执行放入数据和消费数据，不仅在消费数据的时候进行唤醒插入阻塞的线程，同时在插入的时候如果容量还没满，也会唤醒插入阻塞的线程。删除数据同理。
#### 线程安全的集合
##### ConcurrentHashMap
HashMap不是线程安全的。虽然Hashtable使用synchronized来保证线程安全，但在线程竞争激烈的情况下，所有访问Hashtable的线程都必须竞争同一把锁，因此效率非常低下。
ConcurrentHashMap使用了分段锁技术，降低了锁粒度，每一把锁用于锁容器其中一部分数据，那么当多线程访问容器里不同数据段的数据时，线程间就不会存在锁竞争，从而可以有效的提高并发访问效率。
具体源码为：ConcurrentHashMap同HashMap一样，内部也有链表数组tables，即若干个桶。当对Map中的数据进行修改时，通过Key的hash code找到对应的桶的链表头结点，使用synchronized关键字修饰加锁，保证线程安全性。
##### CopyOnWriteArrayList和CopyOnWriteArraySet
CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
CopyOnWriteArrayList当修改其中数据时，先将内部Object数组进行复制并修改复制后的对象，再重新将内部Object数组变量指向新的数组对象。
CopyOnWriteArraySet内部使用CopyOnWriteArrayList来实现，当调用add()方法时，即调用CopyOnWriteArrayList.addIfAbsent()方法。
#### Callable和Future
#### 同步器
[资料1](http://www.importnew.com/15731.html)
CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
CyclicBarrier和CountDownLatch功能相似，也是等待若干线程都执行完以后再执行。与CountDownLatch区别在于这个计数器可以反复使用。比如，我们将计数器设置为10。那么凑齐第一批10个线程后，计数器就会归零，然后接着凑齐下一批10个线程。
Semaphore翻译为信号量，功能为可以控制同时访问的线程个数，通过acquire()或tryAcquire()获取一个许可，如果没有则等待，而release()释放许可并激活其他等待线程。
CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同。CountDownLatch一般用于某个线程等待其他若干个线程执行完任务之后，它才执行。而CyclicBarrier一般用于一组线程之间互相等待至某个状态，然后这一组线程再同时执行。另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。
Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
### 线程池
[资料1](http://www.importnew.com/19011.html)
[资料2](http://www.importnew.com/24923.html)
[资料3](http://blog.csdn.net/scboyhj__/article/details/48805881)
合理利用线程池能够带来三个好处。第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。第二：提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。
ThreadPoolExecutor的工作原理，总结如下：

1. 如果当前池poolSize小于corePoolSize，则创建新线程执行任务。
1. 如果当前池poolSize大于corePoolSize，且任务等待队列未满，则进入等待队列。
1. 如果当前池poolSize大于corePoolSize且小于maximumPoolSize，且任务等待队列已满，则创建新线程执行任务。
1. 如果当前池poolSize大于corePoolSize且大于maximumPoolSize，且任务等待队列已满，则调用拒绝策略来处理该任务。
1. 线程池里的每个线程执行完任务后不会立刻退出，而是会去检查下等待队列里是否还有线程任务需要执行，如果在keepAliveTime里等不到新的任务了，那么线程就会退出。

不过在java doc中，并不提倡我们直接使用ThreadPoolExecutor，而是使用Executors类中提供的静态工厂方法来创建线程池：
_content_copy_
```
Executors.newCachedThreadPool();		//创建一个缓冲池，缓冲池容量大小Integer.MAX_VALUE
Executors.newSingleThreadExecutor();	//创建容量为1的缓冲池
Executors.newFixedThreadPool(int);	//创建固定容量大小的缓冲池
```
### Fork-Join框架
[资料1](http://www.importnew.com/17565.html)
### Java IO与NIO
[资料1](http://www.importnew.com/22623.html)
Java IO的各种流是阻塞的。这意味着，当一个线程调用read()或write()时，该线程被阻塞，直到有一些数据被读取，或数据完全写入。该线程在此期间不能再干任何事情了。Java NIO的非阻塞模式，使一个线程从某通道发送请求读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取。而不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。非阻塞写也是如此。一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。线程通常将非阻塞IO的空闲时间用于在其它通道上执行IO操作，所以一个单独的线程现在可以管理多个输入和输出通道（channel）。
## 新特性1.8
[资料1](http://www.importnew.com/11908.html)
## JNI
[资料1](http://www.cnblogs.com/rocomp/p/4892866.html)
## 其他
