pipeline {
  agent {
	
    kubernetes {
      label 'promo-app'  // all your pods will be named with this prefix, followed by a unique id
      idleMinutes 5  // how long the pod will live after no jobs have run on it
      yamlFile 'build-pod.yaml'  // path to the pod definition relative to the root of our project 
      defaultContainer 'maven'  // define a default container if more than a few stages use it, will default to jnlp container
    }
  }
  stages {
	
    stage('Compile') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn clean compile"   

      }
      
    }
 stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
 stage('Static Code Analysis') {
            steps {
                sh 'echo "sonarqube"'
            }
        }
    stage('Package') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn clean package"  
    }
  }
    stage('Push Image') {
      steps{
	container('docker') {
		script{
	docker.withRegistry('https://registry.hub.docker.com'  , 'docker') {   	
	  def myImg = docker.image('kirtiazad11111/test')
		 sh "docker build  -t ${myImg.imageName()}:${env.BUILD_NUMBER} ."
  			// or docker.build, etc.
 		 sh "docker push  ${myImg.imageName()}:${env.BUILD_NUMBER}"

          }
		}
        }
      }
    }
	            
  //    stage('Approval Dev') {
  //             agent none
 //           steps {
 //               input "Does the Dev environment look ok?"
  //          }
 //       }
  
      stage('Deploy Dev') {
            steps {
		    container('kubectl') {
            script {
          withCredentials([ string(credentialsId: 'kubeconfig', variable: 'kubeconfig') ]) {
		  byte[] decoded = kubeconfig.decodeBase64()
	          println new String(decoded)
		  def config = new String(decoded)
         //   print 'kubeconfig=' + kubeconfig
		  sh "echo  $config > configfile"
		  sh "kubectl apply -k ./overlays/staging/  --kubeconfig=configfile"
          }
        }
		    }
            
        }
  }
  }
	
      post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }

}
