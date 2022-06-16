package com.example.application.views.helloworld;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@PageTitle("Hello World")
@Route(value = "")
//@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends VerticalLayout {

    //private TextField gx;
    //private TextField gy;

    private NumberUtil n = new NumberInput("n");
    private NumberUtil gxInput = new NumberInput("g.x");
    private NumberUtil gyInput = new NumberInput("g.y");
    private NumberUtil PublicKeyXInput = new KeyInput("Public Key.X");
    private NumberUtil PublicKeyYInput = new KeyInput("Public Key.Y");

    private NumberUtil PrivateKeyInput = new KeyInput("Private Key");

    private Button ComputePublicKey;

    private Button GeneratePrivateKey;

    private TextField MessageTextField;

    private Button SignButton;

    private Button VerifyButton;


    private NumberUtil RInput = new KeyInput("R value");
    private NumberUtil SInput = new KeyInput("S value");



    ECDSA ecdsa = new ECDSA();

    public HelloWorldView() {
        ComputePublicKey = new Button("Compute public key");
        GeneratePrivateKey = new Button("Generate private key");
        MessageTextField = new TextField("Message");
        SignButton = new Button("Sign message");
        VerifyButton = new Button("Verify message");


        //g = new Label("g");
        //gx = new TextField("g.x");
        //gy = new TextField("g.y");
//        publicKeyX = new TextField("publicKeyX");
//        publicKeyY = new TextField("publicKeyY");

        n.textField.setValue("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141");
        n.select.setValue("Hexadecimal - 16");

        gxInput.textField.setValue("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798");
        gxInput.select.setValue("Hexadecimal - 16");

        gyInput.textField.setValue("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8");
        gyInput.select.setValue("Hexadecimal - 16");

        n.setWidth("70%");
        gxInput.setWidth("70%");
        gyInput.setWidth("70%");

        PublicKeyXInput.setWidth("100%");
        PublicKeyYInput.setWidth("100%");
        PrivateKeyInput.setWidth("70%");
        RInput.setWidth("70%");
        SInput.setWidth("70%");
        ComputePublicKey.setEnabled(false);

        GeneratePrivateKey.addClickListener(e->{
            ComputePublicKey.setEnabled(true);
            Random random = new Random();
            ecdsa.PrivateKey = new BigInteger(256, random);
            this.PrivateKeyInput.textField.setValue(ecdsa.PrivateKey.toString(16));
            this.PrivateKeyInput.select.setValue("Hexadecimal - 16");
        });

        ComputePublicKey.addClickListener(e -> {

            BigIntegerInput nVal = new BigIntegerInput(n.textField.getValue(),n.select.getValue());
            ecdsa.N = nVal.getValue();
            BigIntegerInput gxVal = new BigIntegerInput(gxInput.textField.getValue(), gxInput.select.getValue());
            ecdsa.Gx = gxVal.getValue();
            BigIntegerInput gyVal = new BigIntegerInput(gyInput.textField.getValue(), gyInput.select.getValue());
            ecdsa.Gy = gyVal.getValue();

            ecdsa.computePublicKey();
            PublicKeyXInput.textField.setValue(ecdsa.PublicKeyX.toString(16));
            PublicKeyYInput.textField.setValue(ecdsa.PublicKeyY.toString(16));
            PublicKeyXInput.select.setValue("Hexadecimal - 16");
            PublicKeyYInput.select.setValue("Hexadecimal - 16");
            PublicKeyXInput.select.setReadOnly(false);
            PublicKeyYInput.select.setReadOnly(false);



            //Notification.show("Hello " + nVal.getValue() );
            //PublicKeyYInput.textField.setValue("123");


        });



        SignButton.addClickListener(e->{
            if(ecdsa.PrivateKey.equals(ecdsa.PublicKeyX)||ecdsa.PublicKeyX.equals(BigInteger.ZERO)){
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                Div text = new Div(new Text("Can't sign: key was not generated!"));
                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });
                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);
                notification.add(layout);
                notification.setDuration(15000);
                notification.open();
            }
            else if(MessageTextField.isEmpty()){
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                Div text = new Div(new Text("Can't sign: message is empty!"));
                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });
                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);
                notification.add(layout);
                notification.setDuration(15000);
                notification.open();
            }
            else {
                try {
                    ecdsa.sign(MessageTextField.getValue());
                    RInput.textField.setValue(ecdsa.R.toString(16));
                    SInput.textField.setValue(ecdsa.S.toString(16));

                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }

        });

        VerifyButton.addClickListener(e -> {
            if(ecdsa.PrivateKey.equals(ecdsa.PublicKeyX)){
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                Div text = new Div(new Text("Can't verify: key was not generated!"));
                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });
                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);
                notification.add(layout);
                notification.setDuration(15000);
                notification.open();
            }
            else if(ecdsa.S.equals(ecdsa.R)){
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                Div text = new Div(new Text("Can't verify: sign was not generated!"));
                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.getElement().setAttribute("aria-label", "Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });
                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);
                notification.add(layout);
                notification.setDuration(15000);
                notification.open();
            }
            else{
                try{
                    boolean sign = ecdsa.verify(MessageTextField.getValue());

                    Notification notification = new Notification();
                    Div text;
                    if (sign) {
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        text =  new Div(new Text("Verify: sign is correct!"));
                    }
                    else {
                        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        text =  new Div(new Text("Verify: sign is not correct!"));
                    }
                    Button closeButton = new Button(new Icon("lumo", "cross"));
                    closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                    closeButton.getElement().setAttribute("aria-label", "Close");
                    closeButton.addClickListener(event -> {
                        notification.close();
                    });
                    HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                    layout.setAlignItems(Alignment.CENTER);
                    notification.add(layout);
                    notification.setDuration(15000);
                    notification.open();



                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }

            }

        });




        setMargin(true);
        add(
                n,
                gxInput,
                gyInput,
                GeneratePrivateKey,
                PrivateKeyInput,
                ComputePublicKey,
                PublicKeyXInput,
                PublicKeyYInput,
                MessageTextField,
                SignButton,
                RInput,
                SInput,
                VerifyButton
        );

        PublicKeyXInput.select.setReadOnly(true);
        PublicKeyYInput.select.setReadOnly(true);

    }

}
