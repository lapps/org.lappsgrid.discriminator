
help:
	@echo "GOALS"
	@echo "     clean : removes artifacts from previous builds."
	@echo "       jar : compiles and packages the project."
	@echo "    deploy : deploys the module to the Nexus repository."
	@echo "      site : generates the Maven site and pushes it to Github"
	@echo
	
clean:
	mvn clean
	
jar:
	mvn package
	
deploy:
	mvn javadoc:jar source:jar deploy
	
site:
	mvn site
	cp target/site ../org.lappsgrid.discriminator.pages
	cd ../org.lappsgrid.discriminator.pages; commit "Updated Maven site." ; push
	
