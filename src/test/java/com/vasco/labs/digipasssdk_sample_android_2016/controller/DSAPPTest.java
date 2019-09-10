package com.vasco.labs.digipasssdk_sample_android_2016.controller;

import org.junit.Test;
import static org.junit.Assert.*;

public class DSAPPTest {

    @Test
    public void dsapp_validateSRPUserPasswordChecksum_returnsTrue(){
        assertTrue(DSAPP.validateSRPUserPasswordChecksum("user", "user"));
    }

    @Test
    public void dsapp_validateSRPUserPasswordChecksum_returnsFalse(){
        assertFalse(DSAPP.validateSRPUserPasswordChecksum("NOT_VALID", "NOT_VALID"));
    }

    @Test
    public void dsapp_generateSRPClientEphemeralKeyAndSessionKey_returnsTrue(){
        assertTrue(DSAPP.generateSRPClientEphemeralKeyAndSessionKey("VALID", "key", "salt"));
    }

    @Test
    public void dsapp_generateSRPClientEphemeralKeyAndSessionKey_returnsFalse(){
        assertFalse(DSAPP.generateSRPClientEphemeralKeyAndSessionKey("NOT_VALID", "key", "salt"));
    }

    @Test
    public void dsapp_verifySRPServerEvidenceMessageAndDecryptSRPData_returnsTrue(){
        assertTrue(DSAPP.verifySRPServerEvidenceMessageAndDecryptSRPData("evidence", "encrypted", "counter", "mac"));
    }

    @Test
    public void dsapp_verifySRPServerEvidenceMessageAndDecryptSRPData_returnsFalse(){
        assertFalse(DSAPP.verifySRPServerEvidenceMessageAndDecryptSRPData("NOT_VALID", "encrypted", "counter", "mac"));
    }
}
