## How to manage project with mx build tool

### System setup
- Download `mx`, a compatible JDK, and GraalVM, and setup necessary environment variables as done in your `set-env.sh` script.
- Alternatively, you can download a compatible JDK with: 
```
mx fetch-jdk --java-distribution labsjdk-ce-11
export JAVA_HOME=PATH_TO_THE_DOWNLOADED_JDK

```

### Project structure
- Here we try to build the instrumentation tool (polytaint) project with mx.
- Create project folder/suite `top`. This will contain all the sub-folders with program code.
- Create `mx.top` folder in suite folder. Create `suite.py` in this directory.
- Fill `source.py` just as in this project. Check other mx projects for more guidance/inspiration on more fields and options to add.
- Before coding or building your project, get dependent projects with `mx sforceimports`.
- Build your project with `mx build`.