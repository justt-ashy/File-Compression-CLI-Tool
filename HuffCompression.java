import java.util.*;
import java.io.*;
import java.util.Comparator;

class ByteNode {
    Byte data;
    int frequency;
    ByteNode left, right;

    public ByteNode(Byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }
}

public class HuffCompression {
    private static Map<Byte, String> huffmap = new HashMap<>();

    public static void compress(String src, String dst) {
        try (FileInputStream inStream = new FileInputStream(src);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            byte[] temp = new byte[1024];
            int bytesRead;
            while ((bytesRead = inStream.read(temp)) != -1) {
                buffer.write(temp, 0, bytesRead);
            }
            byte[] b = buffer.toByteArray();

            byte[] huffmanBytes = createZip(b);

            try (ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(dst))) {
                objectOutStream.writeObject(huffmanBytes);
                objectOutStream.writeObject(huffmap);
            }

        } catch (IOException e) {
            System.err.println("Compression error: " + e.getMessage());
        }
    }

    private static byte[] createZip(byte[] bytes) {
        PriorityQueue<ByteNode> nodes = new PriorityQueue<>(
            Comparator.comparingInt(n -> n.frequency)
        );

        Map<Byte, Integer> freqMap = new HashMap<>();
        for (byte b : bytes) {
            freqMap.put(b, freqMap.getOrDefault(b, 0) + 1);
        }

        for (Map.Entry<Byte, Integer> entry : freqMap.entrySet()) {
            nodes.add(new ByteNode(entry.getKey(), entry.getValue()));
        }

        while (nodes.size() > 1) {
            ByteNode left = nodes.poll();
            ByteNode right = nodes.poll();
            ByteNode parent = new ByteNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
        }

        generateCodes(nodes.poll(), new StringBuilder());
        return zipBytes(bytes);
    }

    private static void generateCodes(ByteNode node, StringBuilder code) {
        if (node == null) return;
        if (node.data != null) {
            huffmap.put(node.data, code.toString());
            return;
        }
        generateCodes(node.left, new StringBuilder(code).append('0'));
        generateCodes(node.right, new StringBuilder(code).append('1'));
    }

    private static byte[] zipBytes(byte[] bytes) {
        StringBuilder bitString = new StringBuilder();
        for (byte b : bytes) {
            bitString.append(huffmap.get(b));
        }
        int padding = (8 - (bitString.length() % 8)) % 8;
        bitString.append("0".repeat(padding));
        byte[] result = new byte[bitString.length() / 8];
        for (int i = 0; i < result.length; i++) {
            String byteStr = bitString.substring(i * 8, (i + 1) * 8);
            result[i] = (byte) Integer.parseInt(byteStr, 2);
        }
        return result;
    }

    public static void decompress(String src, String dst) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src));
             FileOutputStream fos = new FileOutputStream(dst)) {

            byte[] huffmanBytes = (byte[]) ois.readObject();

            @SuppressWarnings("unchecked")
            Map<Byte, String> codes = (Map<Byte, String>) ois.readObject();

            fos.write(decompress(huffmanBytes, codes));

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Decompression error: " + e.getMessage());
        }
    }

    private static byte[] decompress(byte[] huffmanBytes, Map<Byte, String> codes) {
        StringBuilder bitString = new StringBuilder();
        for (byte b : huffmanBytes) {
            bitString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF))
                .replace(' ', '0'));
        }
        Map<String, Byte> reverseMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : codes.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        List<Byte> result = new ArrayList<>();
        StringBuilder currentCode = new StringBuilder();
        for (char bit : bitString.toString().toCharArray()) {
            currentCode.append(bit);
            if (reverseMap.containsKey(currentCode.toString())) {
                result.add(reverseMap.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }
        byte[] byteArray = new byte[result.size()];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = result.get(i);
        }
        return byteArray;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the File Compression Tool!");

        // 1. Ask operation
        System.out.print("Do you want to compress or decompress? (c/d): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("c")) {
            String input = "text.txt";
            String output = "text.txt.huff";

            // 2. Overwrite check
            File outFile = new File(output);
            if (outFile.exists()) {
                System.out.print("Output file already exists. Overwrite? (y/n): ");
                String overwrite = scanner.nextLine().trim().toLowerCase();
                if (!overwrite.equals("y")) {
                    System.out.println("Operation cancelled.");
                    return;
                }
            }

            // 3. Confirm before starting
            System.out.print("Ready to compress 'text.txt' to 'text.txt.huff'. Continue? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println("Operation cancelled.");
                return;
            }

            compress(input, output);
            System.out.println("Compressed " + input + " to " + output);

            // 4. Show statistics
            System.out.print("Would you like to see compression statistics? (y/n): ");
            String showStats = scanner.nextLine().trim().toLowerCase();
            if (showStats.equals("y")) {
                File inFile = new File(input);
                File compressedFile = new File(output);
                long originalSize = inFile.length();
                long compressedSize = compressedFile.length();
                double ratio = (originalSize == 0) ? 0 : (100.0 * compressedSize) / originalSize;
                System.out.printf("Original size: %d bytes\nCompressed size: %d bytes\nCompression ratio: %.2f%%\n",
                        originalSize, compressedSize, ratio);
            }

            // 5. Show Huffman codes
            System.out.print("Would you like to view the Huffman codes? (y/n): ");
            String showCodes = scanner.nextLine().trim().toLowerCase();
            if (showCodes.equals("y")) {
                for (Map.Entry<Byte, String> entry : huffmap.entrySet()) {
                    char ch = (char) (byte) entry.getKey();
                    String display = (ch >= 32 && ch <= 126) ? "'" + ch + "'" : "byte " + entry.getKey();
                    System.out.println(display + ": " + entry.getValue());
                }
            }

        } else if (choice.equals("d")) {
            String input = "text.txt.huff";
            String output = "text.txt.dec";

            // Confirm before starting
            System.out.print("Ready to decompress 'text.txt.huff' to 'text.txt.dec'. Continue? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println("Operation cancelled.");
                return;
            }

            decompress(input, output);
            System.out.println("Decompressed " + input + " to " + output);

            // Show file size
            File outFile = new File(output);
            System.out.println("Decompressed file size: " + outFile.length() + " bytes");
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
