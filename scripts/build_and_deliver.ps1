# Build and deliver script for Windows (PowerShell)
# Usage: .\build_and_deliver.ps1 -flavor debug
param(
    [string] $flavor = "Debug"
)

Set-Location "$(Split-Path -Path $MyInvocation.MyCommand.Definition -Parent)/.."
Write-Host "Building Android APK ($flavor)..."

# Gradle wrapper build
if ($flavor -ieq "Release") {
    Write-Host "Ensure you have a keystore and a keystore.properties file at the project root."
}

& .\gradlew assemble${flavor}

if ($LASTEXITCODE -ne 0) {
    Write-Error "Gradle build failed"
    exit 1
}

Write-Host "Build successful. APKs available in app\build\outputs\apk\${flavor.ToLower()}\"
