#!/bin/bash

if [ -f submission.zip ]; then
  old=$(date +%s)
  echo "Backup up old submission as submission-${old}"
  echo
  mv submission.zip submission-"${old}".zip
fi

touch refs.md

zip submission.zip src/sttrswing/controller/GameController.java
zip submission.zip src/sttrswing/controller/GameLoader.java
zip submission.zip src/sttrswing/controller/GameSaver.java
zip submission.zip src/sttrswing/view/guicomponents/Slider.java
zip submission.zip src/sttrswing/view/panels/EnterpriseStatus.java
zip submission.zip src/sttrswing/view/panels/NearbyQuadrantScan.java
zip submission.zip src/sttrswing/view/panels/Options.java
zip submission.zip src/sttrswing/view/panels/PhaserAttack.java
zip submission.zip src/sttrswing/view/panels/QuadrantNavigation.java
zip submission.zip src/sttrswing/view/panels/QuadrantScan.java
zip submission.zip src/sttrswing/view/panels/Shield.java
zip submission.zip src/sttrswing/view/panels/Torpedo.java
zip submission.zip src/sttrswing/view/panels/WarpNavigation.java
zip submission.zip src/sttrswing/view/StandardLayoutView.java
zip submission.zip src/sttrswing/view/StartView.java
zip submission.zip src/sttrswing/view/View.java

zip submission.zip test/sttrswing/model/EntityTest.java
zip submission.zip test/sttrswing/model/KlingonTest.java
zip submission.zip test/sttrswing/model/StarbaseTest.java
zip submission.zip test/sttrswing/model/EnterpriseTest.java
zip submission.zip test/sttrswing/model/QuadrantTest.java

zip submission.zip refs.md
