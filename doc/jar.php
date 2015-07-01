<?php
  header("Location: ckjm_ext_20.jar");
  $plik = fopen("licz.txt","r");
  $licznik = fgets($plik);
  fclose($plik);
  $licznik+=1;

  $plik = fopen("licz.txt","w");
  fwrite($plik, $licznik);
  fclose($plik);
?>

