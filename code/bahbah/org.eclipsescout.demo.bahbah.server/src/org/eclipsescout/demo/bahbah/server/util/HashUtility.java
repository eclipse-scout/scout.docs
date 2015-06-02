/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.bahbah.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtility {

  public final static int DEFAULT_NUM_CYCLES = 3557;

  /**
   * creates a new random salt (64bits) according to the SHA1PRNG algorithm.
   * 
   * @return 8byte with the random salt.
   * @throws NoSuchAlgorithmException
   */
  public static byte[] createSalt() throws NoSuchAlgorithmException {
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[8]; // 64 bits
    random.nextBytes(salt);
    return salt;
  }

  /**
   * creates a SHA-512 hash using the given data input and salt.
   * 
   * @param data
   *          the data to hash
   * @param salt
   *          the salt to use
   * @return the hash
   * @throws NoSuchAlgorithmException
   */
  public static byte[] hash(byte[] data, byte[] salt) throws NoSuchAlgorithmException {
    return hash(data, salt, DEFAULT_NUM_CYCLES);
  }

  /**
   * creates a SHA-512 hash using the given data input and salt.
   * 
   * @param data
   *          the data to hash
   * @param salt
   *          the salt to use
   * @param cycles
   *          the number of cycles to re-hash. There is always at least one cylce executed.
   * @return the hash
   * @throws NoSuchAlgorithmException
   */
  public static byte[] hash(byte[] data, byte[] salt, int cycles) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-512");
    digest.reset();
    digest.update(salt);
    byte[] key = digest.digest(data);
    for (int i = 1; i < cycles; i++) {
      digest.reset();
      key = digest.digest(key);
    }
    return key;
  }
}
