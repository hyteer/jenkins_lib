// vars/buildPlugin.groovy
def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    def buildNode = config.node
    def GIT_OPS_URL = config.gitUrl
    def GIT_SRV_URL =  "https://github.com/hyteer/${config.repoName}.git"
    println "Build Node: ${buildNode}"


    // now build, based on the configuration provided
    node ('node-5') {

        //checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: srvName]], submoduleCfg: [], userRemoteConfigs: [[url: gitUrl]]])
        //git url: "https://github.com/jenkinsci/${config.name}-plugin.git"
        stage ('Preparation') {
          checkout([$class: 'GitSCM', branches: [[name: '*/debug']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '83edf549-cf8a-4056-9ed2-d8b635a4a5ee', url: GIT_OPS_URL]]])
        }

        def testName = config.repoName
        println "TestName: ${testName}"
        sh 'printenv'
        sh 'echo "Currdir:${PWD}"'
        sh 'echo "YT-srv:${srvName}"'
        echo "MyService: ${config.srvName}"
        echo "MyRepo: ${config.repoName}"
        //sh "ls"
        //git url: "https://github.com/hyteer/${config.repoName}.git"
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: config.srvName]], submoduleCfg: [], userRemoteConfigs: [[url: GIT_SRV_URL]]])

        //mail to: "...", subject: "${config.name} plugin build", body: "..."
    }
}
