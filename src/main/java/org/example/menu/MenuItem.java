package org.example.menu;

public class MenuItem {
    private String name;
    MenuPat menuPat;
    public MenuItem(String name, MenuPat menuPat) {
        this.name = name;
        this.menuPat = menuPat;
    }
    public String getName() {
        return this.name;
    }
    public void runMethod(){
        this.menuPat.execute();
    }
}
