#### 1、CopyOnWriteArrayList

读取是完全不用加锁的，写入也不会阻塞读取操作。只有写入和写入之间需要进行同步等待。

CopyOnWriteArrayList 类的所有可变操作（add，set 等等）都是通过创建底层数组的新副本来实现的。当 List 需要被修改的时候，并不修改原有内容，而是对原有数据进行一次复制，将修改的内容写入副本。写完之后，再将修改完的副本替换原来的数据，这样就可以保证写操作不会影响读操作了。

<img src="https://pics3.baidu.com/feed/0ff41bd5ad6eddc45b5024cac6559ff5536633e6.jpeg?token=699798863bd621e68ab89a5af7293744&s=1BA478221F9E45C85CC3205E0200D0F2" alt="img" style="zoom:50%;" />

- 