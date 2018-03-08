package com.ledger.u2f;


import apdu4j.ISO7816;
import org.junit.Test;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class U2FTest extends SimulatorTestBase {
    private final static byte[] attestatioPrivkey = new byte[]{(byte) 0xf3, (byte) 0xfc, (byte) 0xcc, (byte) 0x0d, (byte) 0x00, (byte) 0xd8, (byte) 0x03, (byte) 0x19, (byte) 0x54, (byte) 0xf9, (byte) 0x08, (byte) 0x64, (byte) 0xd4, (byte) 0x3c, (byte) 0x24, (byte) 0x7f, (byte) 0x4b, (byte) 0xf5, (byte) 0xf0, (byte) 0x66, (byte) 0x5c, (byte) 0x6b, (byte) 0x50, (byte) 0xcc, (byte) 0x17, (byte) 0x74, (byte) 0x9a, (byte) 0x27, (byte) 0xd1, (byte) 0xcf, (byte) 0x76, (byte) 0x64};
    private final static byte[] attestationCert = new byte[]{(byte) 0x30, (byte) 0x82, (byte) 0x01, (byte) 0x3c, (byte) 0x30, (byte) 0x81, (byte) 0xe4, (byte) 0xa0, (byte) 0x03, (byte) 0x02, (byte) 0x01, (byte) 0x02, (byte) 0x02, (byte) 0x0a, (byte) 0x47, (byte) 0x90, (byte) 0x12, (byte) 0x80, (byte) 0x00, (byte) 0x11, (byte) 0x55, (byte) 0x95, (byte) 0x73, (byte) 0x52, (byte) 0x30, (byte) 0x0a, (byte) 0x06, (byte) 0x08, (byte) 0x2a, (byte) 0x86, (byte) 0x48, (byte) 0xce, (byte) 0x3d, (byte) 0x04, (byte) 0x03, (byte) 0x02, (byte) 0x30, (byte) 0x17, (byte) 0x31, (byte) 0x15, (byte) 0x30, (byte) 0x13, (byte) 0x06, (byte) 0x03, (byte) 0x55, (byte) 0x04, (byte) 0x03, (byte) 0x13, (byte) 0x0c, (byte) 0x47, (byte) 0x6e, (byte) 0x75, (byte) 0x62, (byte) 0x62, (byte) 0x79, (byte) 0x20, (byte) 0x50, (byte) 0x69, (byte) 0x6c, (byte) 0x6f, (byte) 0x74, (byte) 0x30, (byte) 0x1e, (byte) 0x17, (byte) 0x0d, (byte) 0x31, (byte) 0x32, (byte) 0x30, (byte) 0x38, (byte) 0x31, (byte) 0x34, (byte) 0x31, (byte) 0x38, (byte) 0x32, (byte) 0x39, (byte) 0x33, (byte) 0x32, (byte) 0x5a, (byte) 0x17, (byte) 0x0d, (byte) 0x31, (byte) 0x33, (byte) 0x30, (byte) 0x38, (byte) 0x31, (byte) 0x34, (byte) 0x31, (byte) 0x38, (byte) 0x32, (byte) 0x39, (byte) 0x33, (byte) 0x32, (byte) 0x5a, (byte) 0x30, (byte) 0x31, (byte) 0x31, (byte) 0x2f, (byte) 0x30, (byte) 0x2d, (byte) 0x06, (byte) 0x03, (byte) 0x55, (byte) 0x04, (byte) 0x03, (byte) 0x13, (byte) 0x26, (byte) 0x50, (byte) 0x69, (byte) 0x6c, (byte) 0x6f, (byte) 0x74, (byte) 0x47, (byte) 0x6e, (byte) 0x75, (byte) 0x62, (byte) 0x62, (byte) 0x79, (byte) 0x2d, (byte) 0x30, (byte) 0x2e, (byte) 0x34, (byte) 0x2e, (byte) 0x31, (byte) 0x2d, (byte) 0x34, (byte) 0x37, (byte) 0x39, (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x38, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x31, (byte) 0x35, (byte) 0x35, (byte) 0x39, (byte) 0x35, (byte) 0x37, (byte) 0x33, (byte) 0x35, (byte) 0x32, (byte) 0x30, (byte) 0x59, (byte) 0x30, (byte) 0x13, (byte) 0x06, (byte) 0x07, (byte) 0x2a, (byte) 0x86, (byte) 0x48, (byte) 0xce, (byte) 0x3d, (byte) 0x02, (byte) 0x01, (byte) 0x06, (byte) 0x08, (byte) 0x2a, (byte) 0x86, (byte) 0x48, (byte) 0xce, (byte) 0x3d, (byte) 0x03, (byte) 0x01, (byte) 0x07, (byte) 0x03, (byte) 0x42, (byte) 0x00, (byte) 0x04, (byte) 0x8d, (byte) 0x61, (byte) 0x7e, (byte) 0x65, (byte) 0xc9, (byte) 0x50, (byte) 0x8e, (byte) 0x64, (byte) 0xbc, (byte) 0xc5, (byte) 0x67, (byte) 0x3a, (byte) 0xc8, (byte) 0x2a, (byte) 0x67, (byte) 0x99, (byte) 0xda, (byte) 0x3c, (byte) 0x14, (byte) 0x46, (byte) 0x68, (byte) 0x2c, (byte) 0x25, (byte) 0x8c, (byte) 0x46, (byte) 0x3f, (byte) 0xff, (byte) 0xdf, (byte) 0x58, (byte) 0xdf, (byte) 0xd2, (byte) 0xfa, (byte) 0x3e, (byte) 0x6c, (byte) 0x37, (byte) 0x8b, (byte) 0x53, (byte) 0xd7, (byte) 0x95, (byte) 0xc4, (byte) 0xa4, (byte) 0xdf, (byte) 0xfb, (byte) 0x41, (byte) 0x99, (byte) 0xed, (byte) 0xd7, (byte) 0x86, (byte) 0x2f, (byte) 0x23, (byte) 0xab, (byte) 0xaf, (byte) 0x02, (byte) 0x03, (byte) 0xb4, (byte) 0xb8, (byte) 0x91, (byte) 0x1b, (byte) 0xa0, (byte) 0x56, (byte) 0x99, (byte) 0x94, (byte) 0xe1, (byte) 0x01, (byte) 0x30, (byte) 0x0a, (byte) 0x06, (byte) 0x08, (byte) 0x2a, (byte) 0x86, (byte) 0x48, (byte) 0xce, (byte) 0x3d, (byte) 0x04, (byte) 0x03, (byte) 0x02, (byte) 0x03, (byte) 0x47, (byte) 0x00, (byte) 0x30, (byte) 0x44, (byte) 0x02, (byte) 0x20, (byte) 0x60, (byte) 0xcd, (byte) 0xb6, (byte) 0x06, (byte) 0x1e, (byte) 0x9c, (byte) 0x22, (byte) 0x26, (byte) 0x2d, (byte) 0x1a, (byte) 0xac, (byte) 0x1d, (byte) 0x96, (byte) 0xd8, (byte) 0xc7, (byte) 0x08, (byte) 0x29, (byte) 0xb2, (byte) 0x36, (byte) 0x65, (byte) 0x31, (byte) 0xdd, (byte) 0xa2, (byte) 0x68, (byte) 0x83, (byte) 0x2c, (byte) 0xb8, (byte) 0x36, (byte) 0xbc, (byte) 0xd3, (byte) 0x0d, (byte) 0xfa, (byte) 0x02, (byte) 0x20, (byte) 0x63, (byte) 0x1b, (byte) 0x14, (byte) 0x59, (byte) 0xf0, (byte) 0x9e, (byte) 0x63, (byte) 0x30, (byte) 0x05, (byte) 0x57, (byte) 0x22, (byte) 0xc8, (byte) 0xd8, (byte) 0x9b, (byte) 0x7f, (byte) 0x48, (byte) 0x88, (byte) 0x3b, (byte) 0x90, (byte) 0x89, (byte) 0xb8, (byte) 0x8d, (byte) 0x60, (byte) 0xd1, (byte) 0xd9, (byte) 0x79, (byte) 0x59, (byte) 0x02, (byte) 0xb3, (byte) 0x04, (byte) 0x10, (byte) 0xdf};
    private final byte[] challenge = new byte[]{(byte) 0x41, (byte) 0x42, (byte) 0xd2, (byte) 0x1c, (byte) 0x00, (byte) 0xd9, (byte) 0x4f, (byte) 0xfb, (byte) 0x9d, (byte) 0x50, (byte) 0x4a, (byte) 0xda, (byte) 0x8f, (byte) 0x99, (byte) 0xb7, (byte) 0x21, (byte) 0xf4, (byte) 0xb1, (byte) 0x91, (byte) 0xae, (byte) 0x4e, (byte) 0x37, (byte) 0xca, (byte) 0x01, (byte) 0x40, (byte) 0xf6, (byte) 0x96, (byte) 0xb6, (byte) 0x98, (byte) 0x3c, (byte) 0xfa, (byte) 0xcb};
    private final byte[] application = new byte[]{(byte) 0xf0, (byte) 0xe6, (byte) 0xa6, (byte) 0xa9, (byte) 0x70, (byte) 0x42, (byte) 0xa4, (byte) 0xf1, (byte) 0xf1, (byte) 0xc8, (byte) 0x7f, (byte) 0x5f, (byte) 0x7d, (byte) 0x44, (byte) 0x31, (byte) 0x5b, (byte) 0x2d, (byte) 0x85, (byte) 0x2c, (byte) 0x2d, (byte) 0xf5, (byte) 0xc7, (byte) 0x99, (byte) 0x1c, (byte) 0xc6, (byte) 0x62, (byte) 0x41, (byte) 0xbf, (byte) 0x70, (byte) 0x72, (byte) 0xd1, (byte) 0xc4};

    @Test
    public void testAttestationCertNotSet() {
        prepareApplet((byte) 0, attestationCert.length, attestatioPrivkey);
        int[] fidoINS = {0x01, 0x02, 0x03};
        for (int ins : fidoINS) {
            CommandAPDU apdu = new CommandAPDU(0x00, ins, 0, 0);
            ResponseAPDU resp = sim.transmitCommand(apdu);
            assertEquals(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED, resp.getSW());
        }
    }

    @Test
    public void testSetAttestationCert() {
        prepareApplet((byte) 0, attestationCert.length, attestatioPrivkey);

        CommandAPDU certApdu = new CommandAPDU(0xF0, 0x01, 0, 0, attestationCert);
        ResponseAPDU certResponse = sim.transmitCommand(certApdu);
        assertEquals(ISO7816.SW_NO_ERROR, certResponse.getSW());
    }

    @Test
    public void testSetAttestationCertAgain() {
        prepareApplet((byte) 0, attestationCert.length, attestatioPrivkey);

        CommandAPDU certApdu = new CommandAPDU(0xF0, 0x01, 0, 0, attestationCert);
        sim.transmitCommand(certApdu);

        ResponseAPDU certResponse = sim.transmitCommand(certApdu);
        assertEquals(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED, certResponse.getSW());
    }

    @Test
    public void testSelectGivesVersion() {
        prepareApplet((byte) 0, attestationCert.length, attestatioPrivkey);

        CommandAPDU certApdu = new CommandAPDU(0xF0, 0x01, 0, 0, attestationCert);
        sim.transmitCommand(certApdu);

        byte[] response = sim.selectAppletWithResult(aid);
        byte[] expected = {'U', '2', 'F', '_', 'V', '2', (byte) 0x90, 0x00};
        assertArrayEquals(expected, response);
    }
}
