@echo OFF
setlocal enabledelayedexpansion

:: Backup existing submission
if not exist submission.zip goto nozip
echo - Backing up existing submission.zip

:: Get the current date and time in a consistent format
for /f "tokens=2 delims==" %%A in ('"wmic os get localdatetime /value"') do set datetime=%%A

:: Extract date and time components
set yy=!datetime:~0,4!
set mm=!datetime:~4,2!
set dd=!datetime:~6,2!
set hh=!datetime:~8,2!
set min=!datetime:~10,2!
set sec=!datetime:~12,2!

:: Format the timestamp
set timestamp=!yy!!mm!!dd!!hh!!min!!sec!

:: Rename the file
ren submission.zip submission-!timestamp!.zip

:nozip

:: See if there is an existing zip on this system
echo - Checking for an installed zip program
set zipath=
"%zipath%zip" >nul 2>nul
if %ERRORLEVEL%==0 echo   Yes, found one && goto COMMON
echo   No, none found.

:: See if we've been here before and already install gnu zip
echo - Checking if we have installed one before
set zipath=%ProgramFiles(x86)%\GnuWin32\bin\
"%zipath%zip" >nul 2>nul
if %ERRORLEVEL%==0 echo   Yes, we have && goto COMMON

echo   No, we haven't. Let's install one.
echo.
echo You be be asked to allow "download.exe" to run.
echo Please click on YES when it asks for permission.
echo.
pause

winget install gnuwin32.zip

:COMMON
"%zipath%zip" submission.zip src/sttrswing/controller/GameController.java
"%zipath%zip" submission.zip src/sttrswing/controller/GameLoader.java
"%zipath%zip" submission.zip src/sttrswing/controller/GameSaver.java
"%zipath%zip" submission.zip src/sttrswing/view/guicomponents/Slider.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/EnterpriseStatus.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/NearbyQuadrantScan.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/Options.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/PhaserAttack.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/QuadrantNavigation.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/QuadrantScan.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/Shield.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/Torpedo.java
"%zipath%zip" submission.zip src/sttrswing/view/panels/WarpNavigation.java
"%zipath%zip" submission.zip src/sttrswing/view/StandardLayoutView.java
"%zipath%zip" submission.zip src/sttrswing/view/StartView.java
"%zipath%zip" submission.zip src/sttrswing/view/View.java

"%zipath%zip" submission.zip test/sttrswing/model/EntityTest.java
"%zipath%zip" submission.zip test/sttrswing/model/KlingonTest.java
"%zipath%zip" submission.zip test/sttrswing/model/StarbaseTest.java
"%zipath%zip" submission.zip test/sttrswing/model/EnterpriseTest.java
"%zipath%zip" submission.zip test/sttrswing/model/QuadrantTest.java

"%zipath%zip" submission.zip refs.md

:DONE
endlocal
