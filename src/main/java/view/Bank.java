package view;

import entity.KlienciEntity;
import entity.TransakcjeEntity;

import javax.persistence.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Bank extends JDialog {
    private JTextField surnameField;
    private JTextField nameField;
    private JTextField addresField;
    private JLabel surnameFld;
    private JButton addButton;
    private JPanel bankPanel;
    private JTextField IdClientField;
    private JLabel idFld;
    private JButton editButton;
    private JLabel nameFld;
    private JLabel addressFld;
    private JButton deleteButton;
    private JButton findButton;
    private JTextField balanceField;
    private JLabel balanceFld;
    private JTextField IdTrField;
    private JTextField principalField;
    private JTextField recipentField;
    private JButton executeButton;
    private JTextField amountField;
    private JLabel trFld;
    private JLabel IdTrFld;
    private JLabel principalFld;
    private JLabel recipentFld;
    private JLabel amountFld;
    private JButton findTButton;
    private JButton nextButton;
    private JButton clearButton;
    private static Bank form;
    private static int i = 0;
    static TypedQuery<TransakcjeEntity> results;
    private static EntityManagerFactory fac;
    private static EntityManager man;
    public Bank() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create();
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                read();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeTransaction();
            }
        });
        findTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findTransaction();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.IdTrField.setText("");
                form.principalField.setText("");
                form.recipentField.setText("");
                form.amountField.setText("");
                form.IdClientField.setText("");
                form.nameField.setText("");
                form.surnameField.setText("");
                form.addresField.setText("");
                form.balanceField.setText("");
                form.nextButton.setEnabled(false);
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(i<results.getResultList().size()) {
                    form.IdTrField.setText(String.valueOf(results.getResultList().get(i).getId()));
                    form.principalField.setText(String.valueOf(results.getResultList().get(i).getIdPrincipal()));
                    form.recipentField.setText(String.valueOf(results.getResultList().get(i).getIdRecipent()));
                    form.amountField.setText(String.valueOf(results.getResultList().get(i).getAmount()));
                    i += 1;
                }
                else {
                    i=0;
                    nextButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "To wszystkie znalezione transakcje");
                }
            }
        });
    }

    public static void main(String[] args) {
        fac = Persistence.createEntityManagerFactory("default");
        man = fac.createEntityManager();
        JFrame mainFrame = new JFrame("Bank");
        form = new Bank();
        mainFrame.setContentPane(form.bankPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        if(!mainFrame.isVisible()){
            man.close();
            fac.close();
        }
    }

    public static void create() {
       // EntityManagerFactory fac;
        //fac = Persistence.createEntityManagerFactory("default");
        //EntityManager man = fac.createEntityManager();
        EntityTransaction tr = man.getTransaction();
        try {
            tr.begin();
            String imie, nazwisko, adres;
            double balans;
            imie = form.nameField.getText();
            nazwisko = form.surnameField.getText();
            adres = form.addresField.getText();
            balans = Double.parseDouble(form.balanceField.getText());
            if (!Objects.equals(imie, "") && !Objects.equals(nazwisko, "") && !Objects.equals(adres, "") && (!Objects.equals(form.balanceField.getText(), ""))) {
                KlienciEntity klient = new KlienciEntity();
                klient.setName(imie);
                klient.setSurname(nazwisko);
                klient.setAddress(adres);
                klient.setBalance(BigDecimal.valueOf(balans));
                man.persist(klient);
                JOptionPane.showMessageDialog(null, "Dodano nowego klienta");
                form.nameField.setText("");
                form.surnameField.setText("");
                form.addresField.setText("");
                form.balanceField.setText("");
                tr.commit();
            } else {
                JOptionPane.showMessageDialog(null, "Uzupełnij wszystkie pola!");
            }
            //tr.commit();
        } finally {
            if (tr.isActive()) tr.rollback();
            man.flush();
            man.clear();
            //man.close();
            //fac.close();
        }
    }

    public static void read() {
        int id = Integer.parseInt(form.IdClientField.getText());
        //EntityManagerFactory fac;
        //fac = Persistence.createEntityManagerFactory("default");
        //EntityManager man = fac.createEntityManager();
        KlienciEntity f;
        try {
            f = man.find(KlienciEntity.class, id);
            form.nameField.setText(f.getName());
            form.surnameField.setText(f.getSurname());
            form.addresField.setText(f.getAddress());
            form.balanceField.setText(String.valueOf(f.getBalance()));
           // man.close();
            //fac.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Brak takiego klienta w bazie");
            form.nameField.setText("");
            form.surnameField.setText("");
            form.addresField.setText("");
            form.balanceField.setText("");
        }
    }

    public static void update() {
        //EntityManagerFactory fac;
        //fac = Persistence.createEntityManagerFactory("default");
        //EntityManager man = fac.createEntityManager();
        int id = Integer.parseInt(form.IdClientField.getText());
        EntityTransaction tr = man.getTransaction();
        KlienciEntity f;
        try {
            tr.begin();
            f = man.find(KlienciEntity.class, id);
            f.setName(form.nameField.getText());
            f.setSurname(form.surnameField.getText());
            f.setAddress(form.addresField.getText());
            f.setBalance(BigDecimal.valueOf(Double.parseDouble(form.balanceField.getText())));
            JOptionPane.showMessageDialog(null, "Edytowano dane klienta");
            tr.commit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Brak takiego klienta w bazie");
            form.nameField.setText("");
            form.surnameField.setText("");
            form.addresField.setText("");
            form.balanceField.setText("");
        } finally {
            if (tr.isActive()) tr.rollback();
            //man.close();
            //fac.close();
        }
    }

    public static void delete() {
        //EntityManagerFactory fac;
        //fac = Persistence.createEntityManagerFactory("default");
        //EntityManager man = fac.createEntityManager();
        EntityTransaction tr = man.getTransaction();
        try {
            tr.begin();
            int id = Integer.parseInt(form.IdClientField.getText());
            KlienciEntity f;
            try {
                f = man.find(KlienciEntity.class, id);
                man.remove(f);
                JOptionPane.showMessageDialog(null, "Usunięto klienta");
                form.nameField.setText("");
                form.surnameField.setText("");
                form.addresField.setText("");
                form.balanceField.setText("");
                tr.commit();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nie ma takiego klienta w bazie banku");
            }
        } finally {
            if (tr.isActive()) tr.rollback();
            //man.close();
            //fac.close();
        }
    }

    public void executeTransaction() {
       // EntityManagerFactory fac;
       // fac = Persistence.createEntityManagerFactory("default");
       // EntityManager man = fac.createEntityManager();
        EntityTransaction tr = man.getTransaction();
        try {
            tr.begin();
            int id_principal, id_recipent;
            double amount;
            if (form.principalField.getText() == null || form.recipentField.getText() == null || form.amountField.getText() == null) {
                JOptionPane.showMessageDialog(null, "Uzupełnij wszystkie pola!");
            } else {
                id_principal = Integer.parseInt(form.principalField.getText());
                id_recipent = Integer.parseInt(form.recipentField.getText());
                amount = Double.parseDouble(form.amountField.getText());
                if (id_principal == id_recipent) {
                    JOptionPane.showMessageDialog(null, "Nie możesz wykonać transakcji na to samo konto");
                } else {
                    KlienciEntity p, r;
                    try {
                        p = man.find(KlienciEntity.class, id_principal);
                        r = man.find(KlienciEntity.class, id_recipent);
                        BigDecimal old_balance_p;
                        BigDecimal new_balance_p;
                        BigDecimal old_balance_r, new_balance_r;
                        old_balance_p = p.getBalance();
                        new_balance_p = old_balance_p.subtract(BigDecimal.valueOf(amount));
                        if (new_balance_p.compareTo(BigDecimal.valueOf(0)) < 0) {
                            JOptionPane.showMessageDialog(null, "Brak środków na zrealizowanie transakacji");
                        } else {
                            p.setBalance(new_balance_p);
                            old_balance_r = r.getBalance();
                            new_balance_r = old_balance_r.add(BigDecimal.valueOf(amount));
                            r.setBalance(new_balance_r);
                            JOptionPane.showMessageDialog(null, "Wykonano przelew");
                            form.IdTrField.setText("");
                            form.principalField.setText("");
                            form.recipentField.setText("");
                            form.amountField.setText("");
                            TransakcjeEntity new_transaction = new TransakcjeEntity();
                            new_transaction.setIdPrincipal(id_principal);
                            new_transaction.setIdRecipent(id_recipent);
                            new_transaction.setAmount(BigDecimal.valueOf(amount));
                            p.setTransakcjesById(new ArrayList<TransakcjeEntity>());
                            p.getTransakcjesById().add(new_transaction);
                            man.persist(new_transaction);
                            tr.commit();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Brak klientów w bazie banku");
                    }
                }
            }
        } finally {
            if (tr.isActive()) tr.rollback();
            //man.close();
            //fac.close();
        }
    }

    public void findTransaction() {
       // EntityManagerFactory fac;
       // fac = Persistence.createEntityManagerFactory("default");
       // EntityManager man = fac.createEntityManager();
        int id;
        if (!Objects.equals(form.IdTrField.getText(), "")) {
            id = Integer.parseInt(form.IdTrField.getText());
            try {
                TransakcjeEntity t = man.find(TransakcjeEntity.class, id);
                form.principalField.setText(String.valueOf(t.getIdPrincipal()));
                form.recipentField.setText(String.valueOf(t.getIdRecipent()));
                form.amountField.setText(String.valueOf(t.getAmount()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Brak transakcji o podanym ID");
            }
        } else {
            if (!Objects.equals(form.principalField.getText(), "")) {
                try {
                    Integer id_principal = Integer.parseInt(form.principalField.getText());
                    results = TransakcjeEntity.getTransactionsByPrincipal(man, id_principal);
                    form.nextButton.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Brak transakcji o podanym ID zleceniodawcy");
                }
            } else if (!Objects.equals(form.recipentField.getText(), "")) {
                try {
                    Integer id_recipent = Integer.parseInt(form.recipentField.getText());
                    results = TransakcjeEntity.getTransactionsByRecipent(man, id_recipent);
                    form.nextButton.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Brak transakcji o podanym ID odbiorcy");
                }
            } else if (!Objects.equals(form.amountField.getText(), "")) {
                try {
                    BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(form.amountField.getText()));
                    results = TransakcjeEntity.getTransactionsByAmount(man, amount);
                    form.nextButton.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Brak transakcji na daną sumę");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Uzupełnij któreś z pól aby wyszukać");
            }
        }
    }
}
