#!/usr/bin/env bash


/usr/lib/jvm/java-8-openjdk-amd64/bin/java -javaagent:/usr/local/lib/ideaIC-2018.2.4/idea-IC-182.4505.22/lib/idea_rt.jar=33265:/usr/local/lib/ideaIC-2018.2.4/idea-IC-182.4505.22/bin -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/cldrdata.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/jaccess.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/nashorn.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunec.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jsse.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management-agent.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:/home/tom/dev/tf2rtl/target/scala-2.12/classes:/home/tom/.ivy2/cache/ch.qos.logback/logback-classic/jars/logback-classic-1.2.3.jar:/home/tom/.ivy2/cache/org.scalatest/scalatest_2.12/bundles/scalatest_2.12-3.0.1.jar:/home/tom/.ivy2/cache/org.scalactic/scalactic_2.12/bundles/scalactic_2.12-3.0.1.jar:/home/tom/.ivy2/cache/org.scalacheck/scalacheck_2.12/jars/scalacheck_2.12-1.13.4.jar:/home/tom/.ivy2/cache/org.scala-lang.modules/scala-parser-combinators_2.12/bundles/scala-parser-combinators_2.12-1.0.4.jar:/home/tom/.ivy2/cache/org.scala-lang/scala-reflect/jars/scala-reflect-2.12.6.jar:/home/tom/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.12.6.jar:/home/tom/.ivy2/cache/org.antlr/antlr4-runtime/jars/antlr4-runtime-4.7.1.jar:/home/tom/.ivy2/cache/net.jcazevedo/moultingyaml_2.12/jars/moultingyaml_2.12-0.4.0.jar:/home/tom/.ivy2/cache/edu.berkeley.cs/treadle_2.12/jars/treadle_2.12-1.0.1.jar:/home/tom/.ivy2/local/edu.berkeley.cs/firrtl_2.12/1.2-SNAPSHOT/jars/firrtl_2.12.jar:/home/tom/.ivy2/cache/edu.berkeley.cs/firrtl-interpreter_2.12/jars/firrtl-interpreter_2.12-1.1.3.jar:/home/tom/.ivy2/cache/edu.berkeley.cs/chisel-iotesters_2.12/jars/chisel-iotesters_2.12-1.2.5.jar:/home/tom/.ivy2/cache/com.thesamet.scalapb/scalapb-runtime_2.12/jars/scalapb-runtime_2.12-0.7.4.jar:/home/tom/.ivy2/cache/com.thesamet.scalapb/lenses_2.12/jars/lenses_2.12-0.7.0.jar:/home/tom/.ivy2/cache/com.lihaoyi/sourcecode_2.12/bundles/sourcecode_2.12-0.1.4.jar:/home/tom/.ivy2/cache/com.lihaoyi/fastparse_2.12/jars/fastparse_2.12-1.0.0.jar:/home/tom/.ivy2/cache/com.lihaoyi/fastparse-utils_2.12/jars/fastparse-utils_2.12-1.0.0.jar:/home/tom/.ivy2/cache/com.github.scopt/scopt_2.12/jars/scopt_2.12-3.7.0.jar:/home/tom/.ivy2/cache/com.github.nscala-time/nscala-time_2.12/jars/nscala-time_2.12-2.14.0.jar:/home/tom/.ivy2/cache/ch.qos.logback/logback-core/jars/logback-core-1.2.3.jar:/home/tom/.ivy2/cache/com.google.protobuf/protobuf-java/bundles/protobuf-java-3.5.1.jar:/home/tom/.ivy2/cache/com.thoughtworks.paranamer/paranamer/bundles/paranamer-2.8.jar:/home/tom/.ivy2/cache/joda-time/joda-time/jars/joda-time-2.9.4.jar:/home/tom/.ivy2/cache/org.fusesource.jansi/jansi/jars/jansi-1.11.jar:/home/tom/.ivy2/cache/org.joda/joda-convert/jars/joda-convert-1.2.jar:/home/tom/.ivy2/cache/org.scala-lang.modules/scala-jline/bundles/scala-jline-2.12.1.jar:/home/tom/.ivy2/cache/org.scala-sbt/test-interface/jars/test-interface-1.0.jar:/home/tom/.ivy2/cache/org.slf4j/slf4j-api/jars/slf4j-api-1.7.25.jar:/home/tom/.ivy2/cache/org.yaml/snakeyaml/bundles/snakeyaml-1.17.jar:/home/tom/.ivy2/cache/com.ibm.icu/icu4j/jars/icu4j-58.2.jar:/home/tom/.ivy2/cache/com.typesafe.scala-logging/scala-logging_2.12/bundles/scala-logging_2.12-3.9.0.jar:/home/tom/.ivy2/cache/org.abego.treelayout/org.abego.treelayout.core/bundles/org.abego.treelayout.core-1.0.3.jar:/home/tom/.ivy2/cache/org.antlr/ST4/jars/ST4-4.0.8.jar:/home/tom/.ivy2/cache/org.antlr/antlr-runtime/jars/antlr-runtime-3.5.2.jar:/home/tom/.ivy2/cache/org.antlr/antlr4/jars/antlr4-4.7.1.jar:/home/tom/.ivy2/cache/org.glassfish/javax.json/bundles/javax.json-1.0.4.jar:/home/tom/.ivy2/cache/org.json4s/json4s-ast_2.12/jars/json4s-ast_2.12-3.6.1.jar:/home/tom/.ivy2/cache/org.json4s/json4s-core_2.12/jars/json4s-core_2.12-3.6.1.jar:/home/tom/.ivy2/cache/org.json4s/json4s-native_2.12/jars/json4s-native_2.12-3.6.1.jar:/home/tom/.ivy2/cache/org.json4s/json4s-scalap_2.12/jars/json4s-scalap_2.12-3.6.1.jar:/home/tom/.ivy2/cache/org.scala-lang.modules/scala-xml_2.12/bundles/scala-xml_2.12-1.0.5.jar:/home/tom/.ivy2/local/edu.berkeley.cs/chisel3_2.12/3.3-SNAPSHOT/jars/chisel3_2.12.jar tf2rtl.Main tf_models/mnist_cnn_v4.pb output/Relu

# cp simple_conv_3x3x$size.rtl simple_conv.rtl

# yosys -s synthesis/simple_conv_synth.ys

# abc -f synthesis/simple_conv_synth.abc
