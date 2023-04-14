# commande pour construire une image docker de chaque service spring boot

Get-ChildItem -Directory | ForEach-Object {
    $dir = $_.Name
    if (Test-Path "$dir/pom.xml") {
        Write-Host "Building image for $dir"
        Set-Location "$dir"
        ./mvnw spring-boot:build-image -DskipTests
        Set-Location ..
    }
}


# commande pour construite l'image docker du service .NET
docker build -t service-comptable:1.0 ./service-facturation


# commande pour construire et executer les containers docker
docker compose build
docker compose up
