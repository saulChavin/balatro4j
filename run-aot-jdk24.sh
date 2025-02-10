export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-24.jdk/Contents/Home/

java -version

if [ ! -e app.aotconf ]; then
  export STARTUP_TIMEOUT=$(date +%s000)
  echo '> TRAINING'
  java -XX:AOTMode=record -XX:AOTConfiguration=app.aotconf -jar build/libs/balatro4j.jar
  echo '> CREATING THE AOTCACHE'
  java -XX:AOTMode=create -XX:AOTConfiguration=app.aotconf -XX:AOTCache=app.aot -jar build/libs/balatro4j.jar
fi

echo '> RUNNING WITH AOTCACHE'
export STARTUP_TIMEOUT=$(date +%s000)
java -XX:AOTCache=app.aot -XX:AOTMode=on -jar build/libs/balatro4j.jar