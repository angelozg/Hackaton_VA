/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.vaudoise.vaapi.dctm.resource.root;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 *
 * @author fatenlabidi
 */
public class LoadAndSetPDF {
    public static  String[] adressParser(String adresse) {
        String[] resultat = new String[4];
        String patternString1 = ("([\\w -'\\.]+)[\\s,]*(\\d+)[\\s,]*(\\d{4})[\\s,]*([\\w-']+)");

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(adresse);

        while(matcher.find()) {
            resultat[0]= matcher.group(1);
            resultat[1]= matcher.group(2);
            resultat[2]= matcher.group(3);
            resultat[3]= matcher.group(4);
//            for(int i=0; i<resultat.length; i++){
//                System.out.println("Affichage tableau : " + resultat[i]);
//            }
        }
        return resultat;
    }

    public static void setPDF(String nom, String prenom, String adresse, String telephone, String commentaire, String description) throws IOException {
        String adressePart1 = null;
        String adressePart2 = null;
        String[] adresseTab = adressParser(adresse);
        if (adresseTab.length>0){
            adressePart1= (adresseTab[0] + " " + adresseTab[1]);
            adressePart2= (adresseTab[2] + " " + adresseTab[3]);
        }
        //Loading an existing document 
        File file = new File("C:\\Temp\\declaration.pdf");
        try (PDDocument document = PDDocument.load(file)) {
            
            PDDocumentCatalog docCatalog = document.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();

            // Get field names
            List<PDField> fieldList = acroForm.getFields();

            // String the object array
            String[] fieldArray = new String[fieldList.size()];
            int i = 0;
            for (PDField sField : fieldList) {
                fieldArray[i] = sField.getFullyQualifiedName();
                i++;
            }

            // Loop through each field in the array and fill the blanks
            for (String f : fieldArray) {
                PDField field = acroForm.getField(f);
//                System.out.println(f);
                switch (f) {
                    case "Nom pr�nom ou raison sociale": {
                        if (nom != null && nom != null) {
                            field.setValue(nom + " " + prenom);
                            break;
                        } else {
                            field.setValue("Faten");
                            break;
                        }
                    }
                    case "Adresse": {
                        if (adressePart1 != null) {
                            field.setValue(adressePart1);
                            break;
                        } else {
                            field.setValue("Rue des moulins 12");
                            break;
                        }
                    }
                    case "NPA et localit�": {
                        if (adressePart2 != null) {
                            field.setValue(adressePart2);
                            break;
                        } else {
                        field.setValue("1006 lausanne");
                        break;
                        }
                    }
                    case "T�l�phone et et fax": {
                        if (telephone != null) {
                            field.setValue(telephone);
                            break;
                        } else {
                            field.setValue("");
                            break;
                        }
                    }
                    case "Email": {
                        field.setValue("");
                        break;
                    }
                    case "Nom de la personne de contact": {
                    	field.setValue(nom + " " + prenom);
                        break;
                    }
                    case "CCP ou compte bancaire": {
                        field.setValue("");
                        break;
                    }
                    case "Si oui N TVA": {
                        field.setValue("");
                        break;
                    }
                    case "Si oui compagnie": {
                        field.setValue("");
                        break;
                    }
                    case "Num�ro de contrat dassurance ex1234562200": {
                        field.setValue("1234564100");
                        break;
                    }
                    case "Remarques": {
                        if (commentaire != null) {
                            field.setValue(commentaire);
                            break;
                        } else {
                            field.setValue("Rien � signaler ...");
                            break;
                        }
                    }
                    case "Description de": {
                    	if (description != null) {
                            field.setValue(description);
                            break;
                        } else {
                            field.setValue("Rien � signaler ...");
                            break;
                        }
                    }
                }
            }
            //Saving the document
            document.save("C:/temp/declarationRemplie.pdf");

            //Closing the document  
            document.close();
        }

    }
}
