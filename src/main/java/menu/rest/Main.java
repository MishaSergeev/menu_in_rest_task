package menu.rest;


import org.hibernate.HibernateException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("Menu");
            em = emf.createEntityManager();
            em.getTransaction().begin();
//            em.persist(new Menu("Fruits", 200, 300, true));
//            em.persist(new Menu("Lobster", 2000, 900, true));
//            em.persist(new Menu("Dorado", 200, 250, true));
//            em.persist(new Menu("Late", 25, 200, false));
//            em.persist(new Menu("Cake", 95, 150, false));
//            em.getTransaction().commit();

            em.createQuery("FROM Menu").getResultList().stream().forEach(System.out::println);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {

        }

            try {
                while (true) {
                    System.out.println("1: add dish");
                    System.out.println("2: price from-to");
                    System.out.println("3: only with discount");
                    System.out.println("4: no more above 1 kg");
                    System.out.println("5: view all menu");
                    System.out.println("6: Exit");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            priceFromTo(sc);
                            break;
                        case "3":
                            onlyDiscount();
                            break;
                        case "4":
                            oneKg(sc);
                            break;
                        case "5":
                            viewAll();
                            break;
                        case "6":
                            sc.close();
                            emf.close();
                            em.close();
                        default:
                            return;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            } finally {

            }
        }


        private static void addDish (Scanner sc){
            System.out.print("Enter name of dish: ");
            String name = sc.nextLine();
            System.out.print("Enter price of dish: ");
            String p = sc.nextLine();
            int price = Integer.parseInt(p);
            System.out.print("Enter weight of dish: ");
            String w = sc.nextLine();
            int weight = Integer.parseInt(w);
            System.out.print("Enter discount(true/false): ");
            Boolean discount = sc.nextBoolean();


            em.getTransaction().begin();
            try {
                Menu c = new Menu(name, price, weight, discount);
                em.persist(c);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            }
        }

        public static void priceFromTo (Scanner sc) throws NoResultException {
            System.out.print("Enter min price of dish: ");

            int priceMin = sc.nextInt();
            System.out.print("Enter max price of dish: ");
            int priceMax = sc.nextInt();
            try {
                Query qu = em.createQuery("SELECT c FROM Menu c WHERE c.price<:b AND c.price>:a", Menu.class);
                qu.setParameter("a", priceMin);
                qu.setParameter("b", priceMax);
                List<Menu> menu = (List<Menu>) qu.getResultList();
                for (Menu m : menu)
                    System.out.println(m);

            } catch (NoResultException ex) {
                System.out.println("Dish not found, try again..");
            }

            return;




        }

        public static void oneKg(Scanner sc){
        int weight = 0;
        while (true){
        System.out.println("Input name of dish:");
        System.out.println("If you want return to main Menu --> press 1 ");
        String name = sc.nextLine();
        Menu menu;
        if(name.equals("1")) {
            break;
        }
        try{
            Query qu = em.createQuery("SELECT c FROM Menu c WHERE c.name=:name", Menu.class);
            qu.setParameter("name", name);
            menu = (Menu) qu.getSingleResult();

            if(weight+menu.getWeight()>1000){
                System.out.println("More then 1 KG ! Try again..");

            }else {
                System.out.println("Your dishes are:"+name);
                weight+=menu.getWeight();
            }

        }catch (NoResultException e){
            System.out.println("Wrong name! Try again..");
        }


        }}

        public static void onlyDiscount () {
            try {
                Query qu = em.createQuery("SELECT c FROM Menu c WHERE c.discount=true ", Menu.class);
                List<Menu> menu = (List<Menu>) qu.getResultList();
                for (Menu m : menu) {
                    System.out.println(m);
                }

            } catch (NoResultException ex) {
                System.out.println("Sorry, we don't have dishes with discount now. Try later..");
            }
            return;
        }

        public static void viewAll () {
            try {
                Query qu = em.createQuery("SELECT c FROM Menu c", Menu.class);
                List<Menu> menu = (List<Menu>) qu.getResultList();
                for (Menu m : menu) {
                    System.out.println(m);
                }

            } catch (NoResultException ex) {
                System.out.println("Sorry, we don't any dishes.Try later..");
            }
            return;
        }
    }











