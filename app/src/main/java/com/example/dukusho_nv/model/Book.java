package com.example.dukusho_nv.model;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;

public class Book  implements Serializable {
  public String title;
  public String cover;
  public String portada;
  public String author;
  public String username;
  public String userimg;
  public String coment;
  public String descargado;

  public String sinopsis;
  public String volumen;
  public String pagina;
  public String estado;
  public String fechadelanzamiento;
  public String imgpj;

  public List<Page> pages;
  public int currentPage;
}
