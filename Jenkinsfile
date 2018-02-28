pipeline {
    environment {
        NEXUS = credentials("local-nexus")
        GITLAB = credentials("gitlab-jenkins")	
    }
    parameters {
        booleanParam(defaultValue: false, description: 'Promote build', name: 'PROMOTE')
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
        stage ('Promote') {
            when {
                environment name: 'PROMOTE', value: 'true'
            }
            steps {
                script {
                    // We want to pick up the version from the pom
                    pom = readMavenPom file: 'pom.xml'
                    version = pom.version.replace("-SNAPSHOT", "-${BUILD_NUMBER}")
                }
                sh '''
                    git reset --hard origin/master
                    git config --local user.email "jenkins@patrikdufresne.com"
                    git config --local user.name "Jenkins"
                '''
                sh "mvn --settings settings.xml -U -Dmaven.test.skip=true -DreleaseVersion=${version} -DdevelopmentVersion=${pom.version} -DpushChanges=false -DlocalCheckout=true -DpreparationGoals=initialize -Dresume=false release:prepare release:perform -B"
                sh "git push http://${GITLAB}@git.patrikdufresne.com/pdsl/minarca.git --tags"
                sh "git push http://${GITLAB}@git.patrikdufresne.com/pdsl/minarca.git "
                
                currentBuild.description = "my new description"
            }
        }
    }
}
