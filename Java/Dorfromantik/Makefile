Make : build/SAE31_2024/Main.class
	jar cvefm SAE31_2024.Main LofiTiles.jar META-INF/manifest.txt -C build SAE31_2024 res

### VARIABLES ###

JC = javac
JV = java
JCFLAGS = -d build -cp build -sourcepath "src:tmp" -encoding UTF-8 -implicit:none
DIR = src/main/java/SAE31_2024

### REGLES ESSENTIELLES ###

build/SAE31_2024/Main.class : $(DIR)/Main.java build/SAE31_2024/controller/GameController.class
	${JC} ${JCFLAGS} $(DIR)/Main.java

build/SAE31_2024/toolbox/ToolBox.class : $(DIR)/toolbox/ToolBox.java
	${JC} ${JCFLAGS} $(DIR)/toolbox/ToolBox.java

build/SAE31_2024/controller/JVolumePanel.class build/SAE31_2024/controller/LofiMusic.class: $(DIR)/controller/JVolumePanel.java $(DIR)/controller/LofiMusic.java build/SAE31_2024/toolbox/ToolBox.class build/SAE31_2024/LaFs/HexSliderUI.class
	${JC} ${JCFLAGS} $(DIR)/controller/JVolumePanel.java $(DIR)/controller/LofiMusic.java

build/SAE31_2024/controller/HexFrame.class build/SAE31_2024/view/HexPanelManagerView.class build/SAE31_2024/controller/HexPanelManagerControler.class build/SAE31_2024/controller/GameController.class build/SAE31_2024/view/LaunchPage.class build/SAE31_2024/view/RulesPage.class build/SAE31_2024/controller/JTopMenu.class build/SAE31_2024/controller/ReturnToMainFrameActionHandler.class build/SAE31_2024/controller/ScoresPage.class build/SAE31_2024/view/HexButton.class build/SAE31_2024/controller/SeriePage.class : $(DIR)/controller/HexFrame.java $(DIR)/view/HexPanelManagerView.java $(DIR)/controller/HexPanelManagerControler.java $(DIR)/controller/GameController.java $(DIR)/view/LaunchPage.java $(DIR)/view/RulesPage.java $(DIR)/controller/JTopMenu.java $(DIR)/controller/ReturnToMainFrameActionHandler.java $(DIR)/controller/ScoresPage.java $(DIR)/controller/SeriePage.java build/SAE31_2024/toolbox/ToolBox.class build/SAE31_2024/model/HexPanelManagerModel.class build/SAE31_2024/model/HexTileModel.class build/SAE31_2024/controller/HexTileController.class build/SAE31_2024/controller/HexPanelTilePreview.class build/SAE31_2024/controller/JVolumePanel.class
	${JC} ${JCFLAGS} $(DIR)/controller/HexFrame.java $(DIR)/view/HexPanelManagerView.java $(DIR)/controller/HexPanelManagerControler.java $(DIR)/controller/GameController.java $(DIR)/view/LaunchPage.java $(DIR)/view/RulesPage.java $(DIR)/controller/JTopMenu.java $(DIR)/controller/ReturnToMainFrameActionHandler.java $(DIR)/controller/ScoresPage.java $(DIR)/view/HexButton.java $(DIR)/controller/ScoresPage.java $(DIR)/controller/SeriePage.java

build/SAE31_2024/controller/HexPanelTilePreview.class : $(DIR)/controller/HexPanelTilePreview.java build/SAE31_2024/toolbox/ToolBox.class
	${JC} ${JCFLAGS} $(DIR)/controller/HexPanelTilePreview.java

build/SAE31_2024/controller/HexTileController.class : $(DIR)/controller/HexTileController.java build/SAE31_2024/model/HexTileModel.class build/SAE31_2024/view/HexTileView.class
	${JC} ${JCFLAGS} $(DIR)/controller/HexTileController.java

build/SAE31_2024/LaFs/HexSliderUI.class : $(DIR)/LaFs/HexSliderUI.java build/SAE31_2024/toolbox/ToolBox.class
	${JC} ${JCFLAGS} $(DIR)/LaFs/HexSliderUI.java

build/SAE31_2024/model/HexPanelManagerModel.class : $(DIR)/model/HexPanelManagerModel.java build/SAE31_2024/model/HexTileModel.class
	${JC} ${JCFLAGS} $(DIR)/model/HexPanelManagerModel.java

build/SAE31_2024/model/HexTileModel.class build/SAE31_2024/model/BiomesBags.class: $(DIR)/model/HexTileModel.java $(DIR)/model/BiomesBags.java build/SAE31_2024/toolbox/ToolBox.class
	${JC} ${JCFLAGS} $(DIR)/model/HexTileModel.java $(DIR)/model/BiomesBags.java

build/SAE31_2024/view/HexTileView.class : $(DIR)/view/HexTileView.java build/SAE31_2024/toolbox/ToolBox.class
	${JC} ${JCFLAGS} $(DIR)/view/HexTileView.java

### REGLES OPTIONNELLES ###

run :
	${JV} -jar LofiTiles.jar

clean :
	rm -rf build
	rm -rf LofiTiles.jar

build:
	mkdir -p build

### BUTS FACTISES ###

.PHONY : run clean mrproper

### FIN ###