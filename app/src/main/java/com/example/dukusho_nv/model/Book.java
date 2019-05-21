package com.example.dukusho_nv.model;

import java.io.Serializable;
import java.util.List;

public class Book  implements Serializable {
  public String title;
  public String cover;
  public String portada;
  public String author;
  public String coment;
  public String descargado;

  public List<Page> pages;
  public int currentPage;
}
