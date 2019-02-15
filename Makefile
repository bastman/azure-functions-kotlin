GRADLE_VERSION=5.2.1

print-%: ; @echo $*=$($*)
guard-%:
	@test ${${*}} || (echo "FAILED! Environment variable $* not set " && exit 1)
	@echo "-> use env var $* = ${${*}}";

.PHONY : help
help : Makefile
	@sed -n 's/^##//p' $<

## idea-start:   : start intellij
idea-start:
	open -a /Applications/IntelliJ\ IDEA.app

## gradle-wrapper:   : install gradle wrapper
gradle-wrapper:
	./gradlew --version
	./gradlew wrapper --gradle-version=$(GRADLE_VERSION)
	./gradlew --version


## fn.create-app.nodejs: create app
fn.create-app.nodejs: guard-APP_NAME
	func init $(APP_NAME) --worker-runtime node
	cd $(APP_NAME) && rm -rf .vscode
	make fn.create-function.nodejs APP_NAME=$(APP_NAME) FUNCTION_NAME="Func001"
	make fn.create-function.nodejs APP_NAME=$(APP_NAME) FUNCTION_NAME="Func002"
## fn.create-function.nodejs: create fun
fn.create-function.nodejs: guard-APP_NAME guard-FUNCTION_NAME
	func init $(APP_NAME) --worker-runtime node
	cd $(APP_NAME) && func new --name $(FUNCTION_NAME) --template "HttpTrigger"

## fn.app.start:   : start app
fn.app.start: guard-APP_NAME
	cd $(APP_NAME) && func host start --build
