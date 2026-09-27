    // Data.UUID.js is a thin wrapper over the `uuid` npm package; Java has the
    // v4 generator and the MD5-based v3 in the JDK, so v3/v5 are computed here.
    private static byte[] __uuidNamespaceBytes(String namespace, String name) {
        java.util.UUID ns = java.util.UUID.fromString(namespace);
        byte[] all = new byte[16 + name.getBytes(java.nio.charset.StandardCharsets.UTF_8).length];
        long most = ns.getMostSignificantBits();
        long least = ns.getLeastSignificantBits();
        for (int i = 0; i < 8; i++) all[i] = (byte) (most >>> (8 * (7 - i)));
        for (int i = 0; i < 8; i++) all[8 + i] = (byte) (least >>> (8 * (7 - i)));
        byte[] nameBytes = name.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        System.arraycopy(nameBytes, 0, all, 16, nameBytes.length);
        return all;
    }

    private static String __uuidFromHash(byte[] hash) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (i == 4 || i == 6 || i == 8 || i == 10) builder.append('-');
            builder.append(String.format("%02x", hash[i] & 0xff));
        }
        return builder.toString();
    }

    private static String __hashUuid(String algorithm, int version, int variantMask, int variantBits, String namespace, String name) {
        try {
            byte[] hash = java.security.MessageDigest.getInstance(algorithm).digest(__uuidNamespaceBytes(namespace, name));
            hash[6] = (byte) ((hash[6] & 0x0f) | version);
            hash[8] = (byte) ((hash[8] & variantMask) | variantBits);
            return __uuidFromHash(java.util.Arrays.copyOf(hash, 16));
        } catch (java.security.NoSuchAlgorithmException missing) {
            throw new RuntimeException(missing);
        }
    }

    public static Object getUUIDImpl = (java.util.function.Supplier<Object>) () -> java.util.UUID.randomUUID().toString();

    public static Object validateV4UUID = (java.util.function.Function<Object, Object>) (str) -> {
        try {
            return java.util.UUID.fromString((String) str).version() == 4;
        } catch (IllegalArgumentException invalid) {
            return false;
        }
    };

    public static Object getUUID3Impl = (java.util.function.Function<Object, Object>) (name) ->
        (java.util.function.Function<Object, Object>) (namespace) ->
            __hashUuid("MD5", 0x30, 0x3f, 0x80, (String) namespace, (String) name);

    public static Object getUUID5Impl = (java.util.function.Function<Object, Object>) (name) ->
        (java.util.function.Function<Object, Object>) (namespace) ->
            __hashUuid("SHA-1", 0x50, 0x3f, 0x80, (String) namespace, (String) name);
