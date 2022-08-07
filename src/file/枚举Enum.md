#### **1、String、StringBuffer和StringBuilder的区别**

###### String

[String类](https://so.csdn.net/so/search?q=String类&spm=1001.2101.3001.7020)是不可变类，即一旦一个String对象被创建以后，包含在这个对象中的字符序列是不可改变的，直至这个对象被销毁。

重新赋值，重新申请新的地址空间。

###### [StringBuffer](https://so.csdn.net/so/search?q=StringBuffer&spm=1001.2101.3001.7020)

StringBuffer对象代表可变的字符串，当一个StringBuffer被创建以后，通过StringBuffer提供的append()、insert()、reverse()、setCharAt()、setLength()等方法可以改变这个字符串对象的字符序列。一旦通过StringBuffer生成了最终想要的字符串，就可以调用它的toString()方法将其转换为一个String对象。

StringBuffer类中的方法都添加了**synchronized关键字**，也就是给这个方法添加了一个锁，用来保证线程安全。

###### StringBuilder

StringBuilder类也代表可变字符串对象。实际上，StringBuilder和StringBuffer基本相似，两个类的构造器和方法也基本相同。不同的是：**StringBuffer是线程安全的，而StringBuilder则没有实现线程安全功能，所以性能略高。**

#### 2、==与equals的区别

==对于基本类型，比较的是值，对于引用类型，比较的是内存地址

equals对于引用类型，参考源码，先比较对象地址，地址不同，是String类型会再进行比较字符

```Java
 public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
```

#### 3、重载与重写的区别

 方法的重载和重写都是实现多态的方式，区别在于前者实现的是编译时的多态性，而后者实现的是运行时的多态性。

重载发生在一个类中，同名的方法如果有不同的参数列表（参数类型不同、参数个数不同）则视为重载；

重写发生在子类与父类之间，重写要求子类被重写方法与父类被重写方法有相同的参数列表，有兼容的返回类型，比父类被重写方法更好访问，不能比父类被重写方法声明更多的异常（里氏代换原则）。重载对返回类型没有特殊的要求，不能根据返回类型进行区分。

#### 4、volatile和synchronized; 

volatile 关键字可以保证并发编程三大特征（原子性、可见性、有序性）

（1）保证可见性：

加了volatile关键字修饰的变量，只要有一个线程将主内存中的变量值做了修改，其他线程都将马上收到通知，立即获得最新值。当写线程写一个volatile变量时，JMM会把该线程对应的本地工作内存中的共享变量值刷新到主内存。当读线程读一个volatile变量时，JMM会把该线程对应的本地工作内存置为无效，线程将到主内存中重新读取共享变量。
volatile可见性的实现是借助了CPU的lock指令，lock指令在多核处理器下，可以将当前处理器的缓存行的数据写回到系统内存，同时使其他CPU里缓存了该内存地址的数据置为无效。通过在写volatile的机器指令前加上lock前缀，使写volatile具有以下两个原则：

写volatile时处理器会将缓存写回到主内存。
一个处理器的缓存写回到内存，会导致其他处理器的缓存失效。

（2）保证有序性(禁止指令重排序)

简单说明：

计算机在执行程序时，为了提高计算性能，编译器和处理器常常会对指令进行重排序，一般分为如下3种：

源代码 ——> 编译器优化的重排 ——> 指令并行的重排 ——>内存系统的重排 ——> 最终执行的指令


解释说明：

·单线程环境下，可以确保程序最终执行结果和代码顺序执行结果的一致性（单线程环境下不用关注指令重排，因为是否重排都不会出错）。处理器在进行重排序时，必须要考虑指令之间的数据依赖性。

·多线程环境中，线程交替执行。由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果也就无法预测。而用volatile关键字修饰的变量，可以禁止指令重排序，从而避免多线程环境下，程序出现乱序执行的现象。
（3）不保证原子性：

 原子性指的是，当某个线程正在执行某件事情的过程中，是不允许被外来线程打断的。也就是说，原子性的特点是要么不执行，一旦执行就必须全部执行完毕。而volatile是不能保证原子性的，即执行过程中是可以被其他线程打断甚至是加塞的。

 所以，volatile变量的原子性与synchronized的原子性是不同的。synchronized的原子性是指，只要声明为synchronized的方法或代码块，在执行上就是原子操作的。而volatile是不修饰方法或代码块的，它只用来修饰变量，对于单个volatile变量的读和写操作都具有原子性，但类似于volatile++这种复合操作不具有原子性。所以volatile的原子性是受限制的。并且在多线程环境中，volatile并不能保

#### 5、hashtable为什么是线程安全的；concurrenthashmap为什么是比hashtable高效的； 



[ConcurrentHashMap](https://so.csdn.net/so/search?q=ConcurrentHashMap&spm=1001.2101.3001.7020)为什么能保证线程安全

1. 在put和扩容时使用了synchronized关键字、分段锁

2. 大量使用乐观锁CAS以及volatile的技术

    对于put操作：

   1. 如果Key对应的数组元素为null，则通过CAS操作将其设置为当前值。
   2. 如果Key对应的数组元素（也即链表表头或者树的根元素）不为null，则对该元素使用synchronized关键字申请锁，然后进行操作。

   对于get操作：

   1. 数组被volatile关键字修饰，因此不用担心数组的可见性问题。
   2. 同时每个元素是一个Node实例（Java 7中每个元素是一个HashEntry），它的Key值和hash值都由final修饰，不可变更，无须关心它们被修改后的可见性问题。
   3. 而其Value及对下一个元素的引用由volatile修饰，可见性也有保障。

#### 6、Handler机制，loop方法为何不会造成ANR

Handler机制，说的也是Android中的消息传递机制。将工作线程（子线程）中创建的消息，传递到主线程，以进行UI更新操作的过程。

简单的可理解为，子线程和主线程之间的通讯。

采用这样的机制是为了防止并发更新UI所导致的线程安全问题，而不使用线程锁是为了提高系统的运行效率。

使用

 sendMessage

sendMessage()方法主要是用于发送一条消息。一般用法是通过重写handler的handler消息是通过Message进行封装。如：

post方法主要是post一个Runnable对象，在Runnable的run方法中进行消息的处理，这种方法在使用方面不用自己去创建Message对象，但内部其实还是通过Message实现的。这种方法可以实现在子线程中方便的更新UI而不用去通过重写Handler的handleMessage()方法来更新UI。



相关的类

Handler 用于产生和处理消息

Message 用于消息的封装

MessageQueue 消息队列，用户存储消息。这个MessageQueue不是一个真正的队列，他并没有实现java的Queue接口，他实际上只是封装了一个单向链表，向外界提供了enqueueMessage()、next()等方法。

Looper 轮询器，以轮询的方式从MessageQueue中查询消息，并将消息发送给Handler做处理。

ThreadLocal 用于在线程中存储Looper，可以说实现了线程和Looper的绑定。

大概的流程为：handler发送message到MessageQueue中。Looper轮询MessageQueue，将消息发送给Handler进行处理。

while(true) {//死循环，会不会把主线程卡死？？？

不会被卡死。 涉及到Linux下的通讯机制，管道通讯的概念，管道就是内存中的一个特殊的一个文件，这个文件有两个句柄（其实就是java中的引用），一个是读的引用，一个是写的引用，当写的一端写入内容时，读的一端就会被唤醒，读的一端在没有读到消息时会休眠
内存泄漏使用弱饮用软引用解决。

#### 7、Array和Linked区别

ArrayList是基于数组的，LinkedList是基于链表的。ArrayList适合查询，LinkedList适合插入。

LinkedList不涉及扩容，

ArrayList扩容，扩容后的数组长度 = 当前数组长度 + 当前数组长度 / 2。最后使用 `Arrays.copyOf` 方法直接把原数组中的数组 copy 过来

#### 8、Java 注解（Annotation）

Java 标注可以通过反射获取标注内容。在编译器生成类文件时，标注可以被嵌入到字节码中。Java 虚拟机可以保留标注内容，在运行时可以获取到标注内容 。