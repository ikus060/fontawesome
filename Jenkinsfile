pipeline {
    environment {
        NEXUS = credentials("local-nexus")
        GITLAB = credentials("gitlab-jenkins")
    }
    parameters {
        booleanParam(defaultValue: false, description: 'Generate a release build with a tagged version.', name: 'Release')
    }
    agent {
        docker {
            image 'jamesdbloom/docker-java7-maven'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage ('Build') {
            steps {
                writeFile file: "settings.xml", text: "<settings><servers><server><id>patrikdufresne</id><username>${NEXUS_USR}</username><password>${NEXUS_PSW}</password></server></servers></settings>"
                sh 'mvn --settings settings.xml -U -Dmaven.test.failure.ignore=true clean deploy'
            }
        }
        stage ('Release') {
            when {
                environment name: 'Release', value: 'true'
            }
            steps {
                script {
                    // We want to pick up the version from the pom
                    pom = readMavenPom file: 'pom.xml'
                    version = pom.version.replace("-SNAPSHOT", "-${BUILD_NUMBER}")
                }
                sh 'git config --local user.email "jenkins@patrikdufresne.com"'
                sh 'git config --local user.name "Jenkins"'
                sh 'git checkout .'
                sh "mvn versions:set -DnewVersion=${version}"
                sh "mvn --settings settings.xml -U -Dmaven.test.skip=true deploy"
                sh """
                	git tag 'v${version}'
					export REPO=`git config remote.origin.url`
                    git push http://${GITLAB}@${REPO#*//} --tags
                """
                addInfoBadge "v${version}"
            }
        }
    }
}
