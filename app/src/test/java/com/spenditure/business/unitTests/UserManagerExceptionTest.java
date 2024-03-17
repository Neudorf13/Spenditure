package com.spenditure.business.unitTests;


import com.spenditure.logic.UserManager;
import com.spenditure.logic.UserValidator;
import com.spenditure.logic.exceptions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

public class UserManagerExceptionTest {

    private UserManager userManager;
    private boolean caught;

    @Before
    public void setup(){
        userManager = new UserManager(true);
    }

    @After
    public void teardown(){
        UserManager.cleanup(true);
        this.userManager = null;
    }

    @Test
    public void testLogin() throws NoSuchAlgorithmException {
        caught = false;
        try{
            userManager.login(null,"123");
        }catch (InvalidUserInformationException e){
            caught = true;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.login("null",null);
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.login("","123");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.login("Me","");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.login("Hehe","123");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.login("Me","12233");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        userManager.login("Me","123");
        caught = false;
        try{
            userManager.login("Me","123");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testGetUserID(){
        caught = false;
        try {
            UserManager.getUserID();
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

    }

    @Test
    public void testGetUsername(){
        caught = false;
        try {
            userManager.getUserName(-1);
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testChangePassword() throws NoSuchAlgorithmException {
        int userID = userManager.login("Me","123");


        caught = false;
        try{
            userManager.changePassword(userID,null,"hihi");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changePassword(userID,"null",null);
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changePassword(userID,"","null");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changePassword(userID,"null","");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changePassword(-1,"null","asd");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testRegister(){
        caught = false;
        try{
           userManager.register(null,"null");
        }catch (InvalidUserInformationException e){
            caught = true;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.register("null",null);
        }catch (InvalidUserInformationException e){
            caught = true;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.register("null","");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.register("","aaa");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.register("Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","aaa");
        }catch (InvalidUserInformationException | NoSuchAlgorithmException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testLogOut(){
        caught = false;
        try{
            userManager.logout();
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testChangeUsername() throws NoSuchAlgorithmException {
        int userID = userManager.login("Me","123");
        caught = false;
        try{
            userManager.changeUsername(-1,"me");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changeUsername(userID,"");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changeUsername(userID,null);
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);

        caught = false;
        try{
            userManager.changeUsername(userID,"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }catch (InvalidUserInformationException e){
            caught = true;
        }
        assertTrue(caught);
    }

    @Test
    public void testValidateEmail() {

        boolean caught = false;

        try {
            UserValidator.validateEmail("john.doe@domain.com");
        } catch(InvalidUserInformationException e) {
            caught = true;
        }

        assertFalse(caught);

        try {
            UserValidator.validateEmail("john@doe@domain@com");
        } catch(InvalidUserInformationException e) {
            caught = true;
        }

        assertTrue(caught);

        try {
            UserValidator.validateEmail("john.doe@hahahagotcha");
        } catch(InvalidUserInformationException e) {
            caught = true;
        }

        assertTrue(caught);

        try {
            UserValidator.validateEmail("john.doe@sneaky.trickster.co.uk");
        } catch(InvalidUserInformationException e) {
            caught = true;
        }

        assertTrue(caught);

        try {
            UserValidator.validateEmail("AAAASSAEFQJEPQIJAKLSJDCALNV1-1'4F;MFQA;SDXCÆÍSÍÍÎÅÍ˜Ω≈Œ∏´∑");
        } catch(InvalidUserInformationException e) {
            caught = true;
        }

        assertTrue(caught);
    }

}
