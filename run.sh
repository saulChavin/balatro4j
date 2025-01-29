java -server -XX:+PrintCompilation -XX:+TieredCompilation \
     -XX:CompileThreshold=10000 -XX:Tier2CompileThreshold=15000 \
     -XX:InlineSmallCode=2000 -XX:MaxInlineSize=35 -XX:FreqInlineSize=325 \
     -XX:ReservedCodeCacheSize=256m -XX:CICompilerCount=4 \
     -XX:-UseLoopPredicate -XX:-UseCountedLoopSafepoints -jar build/libs/balatro4j.jar