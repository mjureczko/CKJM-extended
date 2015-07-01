<?php
  header("Location: ckjm_ext.zip");
  $plik = fopen("licz21.txt","r");
  $licznik = fgets($plik);
  fclose($plik);
  $licznik+=1;

  $plik = fopen("licz21.txt","w");
  fwrite($plik, $licznik);
  fclose($plik);
?>
