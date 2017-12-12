# PatchTest
anroid 增量更新

本demo使用Android studio+CMake进行NDK开发
Android的增量更新，主要分两部分；首先是根据新旧apk打出差分包patch，
然后用old.apk与patch合并出new.apk。最后，调用安装new.apk即完成大致的更新

使用该demo时，可先build一个旧的Apk，例如命名为app1.apk。接着可简单的修改versionName，
如从1.0-->1.0.1，然后build出一个新的apk，app2.apk。把两者放进sd卡根目录下，安装app1后，点击“生成差分包”，便可在根目录下
查看到生成了一个差分包为xxx.patch。接着把app2删除，进入app1的主界面点击“合成新的apk”，过一会，在SD卡根目录下会生成app2.apk，这就是合成的新apk，接着会自动提示安装。
这就是大概的测试过程。
