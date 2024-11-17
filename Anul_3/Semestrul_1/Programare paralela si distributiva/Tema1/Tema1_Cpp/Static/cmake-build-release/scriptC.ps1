$param1 = $args[0] # Exe file name
$param2 = $args[1] # Number of threads
$param3 = [int]$args[2] # Number of runs, convert to integer if needed

# Initialize sum variable
$suma = 0

# Run the executable multiple times and accumulate execution time
for ($i = 0; $i -lt $param3; $i++) {
    Write-Host "Rulare" ($i + 1)
    $a = cmd /c .\$param1 $param2 2>&1

    # Convert the output to a numeric value (e.g., float) and add to the sum
    try {
        $time = [double]$a  # Adjust to [int] if your output is integer
        $suma += $time
        Write-Host "Execution time for run $($i + 1): $time"
    }
    catch {
        Write-Host "Error: Output is not numeric: $a"
    }
    Write-Host ""
}

# Calculate average execution time
$media = $suma / $param3
Write-Host "Average execution time:" $media

# Create or append to the .csv file
$csvPath = "outC.csv"
if (!(Test-Path $csvPath)) {
    New-Item $csvPath -ItemType File
    # Write header to CSV
    Set-Content $csvPath 'Tip Matrice,Tip alocare,Nr threads,Timp executie'
}

# Append data to CSV
Add-Content $csvPath ",,$($param2),$media"
