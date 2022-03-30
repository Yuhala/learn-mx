


suite = {
    #------------------------------------------------
    #
    # PROJECT METADATA
    #
    #------------------------------------------------
    "mxversion": "5.317.7",
    "name": "learn-mx",
    "versionConflictResolution": "latest",

    "version": "1.0.0",
    "release": False,
    "groupId": "com.oracle.truffle",

    "developer" : {
    "name" : "Peterson Yuhala",
    "email" : "petersonyuhala@gmail.com",
    "organization" : "IIUN",
    "organizationUrl" : "http://www.unine.ch/iiun/",
  },


    # ---------------------------------------------------
    #
    #  DEPENDENCIES
    #
    # ----------------------------------------------------

    "imports": {
        "suites": [
            {
                "name": "truffle",
                "version": "c541f641249fb5d615aa8e375ddc950d3b5b3715",
                "subdir": True,
                "urls": [
                    {"url": "https://github.com/oracle/graal", "kind": "git"},
                ]
            },
            
        ],
    },


    "projects": {
        "com.oracle.truffle.polyt":{
            "subDir": "src",
            "sourceDirs": ["src"],
            "dependencies": [
                "truffle: TRUFFLE_API",
                "graal-js: GRAALJS",
                "truffle:TruffleJSON",
            ],
            "annotationProcessors": ["truffle:TRUFFLE_DSL_PROCESSOR"],
            "buildEnv": {},              
            "javaCompliance": "11+",
            "use_jdk_headers": True,
            
        },
    },


    "distributions":{
        "POLYTAINT": {
            "subDir": "src",
            # This distribution defines a module.
            "moduleInfo" : {
                "name" : "com.oracle.truffle.tools.polyt",
                "requiresConcealed" : {
                    "org.graalvm.truffle" : [
                        "com.oracle.truffle.api.instrumentation"
                    ],
                },
            },
            "dependencies": [
                "com.oracle.truffle.tools.polyt",
            ],
            "distDependencies" : [
                "truffle:TRUFFLE_API",
            ],
            "maven" : {
              "artifactId" : "polytaint",
            },
            "description" : "Truffle polyglot taint analysis tool.",
            "javadocType" : "api",
        },
    },



    "licenses": {
        "BSD-3": {
            "name": "3-Clause BSD License",
            "url": "http://opensource.org/licenses/BSD-3-Clause",
        },
        "MIT": {
            "name": "MIT License",
            "url": "http://opensource.org/licenses/MIT"
        },
    },

    "defaultLicense": "BSD-3",


}

