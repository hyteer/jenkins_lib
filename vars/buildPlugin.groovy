// vars/buildPlugin.groovy
def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    // now build, based on the configuration provided
    node {

        //checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: srvName]], submoduleCfg: [], userRemoteConfigs: [[url: gitUrl]]])
        //git url: "https://github.com/jenkinsci/${config.name}-plugin.git"
        sh "Service: ${config.srvName}"
        sh "Repo: ${config.repoName}"
        git url: "https://github.com/hyteer/${config.GIT_URL}.git"

        //mail to: "...", subject: "${config.name} plugin build", body: "..."
    }
}
