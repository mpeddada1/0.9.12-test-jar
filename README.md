# 0.9.12-test-jar

This project is to reproduce an issue where the test-jar (despite being explicitly added as a dependency) is not included in the classpath of the `native-image` command when using versions 0.9.12 and later. 

1) `java -version`
```
openjdk version "11.0.15" 2022-04-19
OpenJDK Runtime Environment GraalVM CE 22.1.0 (build 11.0.15+10-jvmci-22.1-b06)
OpenJDK 64-Bit Server VM GraalVM CE 22.1.0 (build 11.0.15+10-jvmci-22.1-b06, mixed mode, sharing)
```
2) `mvn clean install -DskipTests`
3) `cd child-project`. **Note** that this is the project that depends on the child-project-2's test jar as shown [here](https://github.com/mpeddada1/0.9.12-test-jar/blob/eedbb2f4ce171a5bd7a346d35cb630ac3f34f18c/child-project/pom.xml#L27).
4) `mvn test -Pnative`
5) See the following error:

```
========================================================================================================================
GraalVM Native Image: Generating 'native-tests' (executable)...
========================================================================================================================
[1/7] Initializing...                                                                                    (4.7s @ 0.21GB)
 Version info: 'GraalVM 22.2.0 Java 11 CE'
 Java version info: '11.0.16+8-jvmci-22.2-b06'
 C compiler: gcc (linux, x86_64, 11.3.0)
 Garbage collector: Serial GC
 1 user-specific feature(s)
 - org.graalvm.junit.platform.JUnitPlatformFeature
[junit-platform-native] Running in 'test listener' mode using files matching pattern [junit-platform-unique-ids*] found in folder [/usr/local/google/home/mpeddada/IdeaProjects/native-image-experiments/0.9.12-test-jar/child-project/target/test-ids] and its subfolders.
Aug 01, 2022 5:05:13 PM org.junit.vintage.engine.descriptor.RunnerTestDescriptor warnAboutUnfilterableRunner
WARNING: Runner org.junit.internal.runners.ErrorReportingRunner (used on class com.example.MySampleTest) does not support filtering and will therefore be run completely.

Fatal error: java.lang.NoClassDefFoundError: Lcom/anotherpackage/MyTestClass;
	at java.base/java.lang.Class.getDeclaredFields0(Native Method)
	at java.base/java.lang.Class.privateGetDeclaredFields(Class.java:3061)
	at java.base/java.lang.Class.getDeclaredFields(Class.java:2248)
	at org.graalvm.junit.platform.config.core.NativeImageConfiguration.registerAllClassMembersForReflection(NativeImageConfiguration.java:63)
	at org.graalvm.junit.platform.JUnitPlatformFeature.registerTestClassForReflection(JUnitPlatformFeature.java:152)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
	at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:177)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
	at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:177)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
	at java.base/java.util.Iterator.forEachRemaining(Iterator.java:133)
	at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1801)
	at java.base/java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:658)
	at java.base/java.util.stream.ReferencePipeline$7$1.accept(ReferencePipeline.java:274)
	at java.base/java.util.Iterator.forEachRemaining(Iterator.java:133)
	at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1801)
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:484)
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
	at org.graalvm.junit.platform.JUnitPlatformFeature.discoverTestsAndRegisterTestClassesForReflection(JUnitPlatformFeature.java:145)
	at org.graalvm.junit.platform.JUnitPlatformFeature.beforeAnalysis(JUnitPlatformFeature.java:94)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.lambda$runPointsToAnalysis$9(NativeImageGenerator.java:722)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.FeatureHandler.forEachFeature(FeatureHandler.java:78)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.runPointsToAnalysis(NativeImageGenerator.java:722)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.doRun(NativeImageGenerator.java:564)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGenerator.run(NativeImageGenerator.java:521)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.buildImage(NativeImageGeneratorRunner.java:407)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.build(NativeImageGeneratorRunner.java:585)
	at org.graalvm.nativeimage.builder/com.oracle.svm.hosted.NativeImageGeneratorRunner.main(NativeImageGeneratorRunner.java:128)
Caused by: java.lang.ClassNotFoundException: com.anotherpackage.MyTestClass
	at java.base/java.net.URLClassLoader.findClass(URLClassLoader.java:476)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:589)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:522)
	... 34 more
------------------------------------------------------------------------------------------------------------------------
                         0.5s (9.1% of total time) in 8 GCs | Peak RSS: 0.76GB | CPU load: 4.22
========================================================================================================================
Failed generating 'native-tests' after 4.9s.
Error: Image build request failed with exit status 1
com.oracle.svm.driver.NativeImage$NativeImageError: Image build request failed with exit status 1
	at com.oracle.svm.driver.NativeImage.showError(NativeImage.java:1716)
	at com.oracle.svm.driver.NativeImage.build(NativeImage.java:1413)
	at com.oracle.svm.driver.NativeImage.performBuild(NativeImage.java:1374)
	at com.oracle.svm.driver.NativeImage.main(NativeImage.java:1361)
```

6) Switch native-maven-plugin version to `0.9.11` to see successful build. 


