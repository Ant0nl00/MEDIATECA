package mediatecaapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneradorCodigo {
    public static String generarCodigo(String tipo) {
        String prefijo = "";
        switch (tipo) {
            case "LIBRO":
                prefijo = "LIB";
                break;
            case "REVISTA":
                prefijo = "REV";
                break;
            case "CD":
                prefijo = "CD";
                break;
            case "DVD":
                prefijo = "DVD";
                break;
            default:
                throw new IllegalArgumentException("Tipo de material no reconocido");
        }
        
        int numero = obtenerUltimoNumero(prefijo) + 1;
        return String.format("%s%05d", prefijo, numero);
    }

    private static int obtenerUltimoNumero(String prefijo) {
        int numero = 0;

        try (Connection connection = DatabaseConnection.conectar()) {
            String sql = "SELECT codigoIdentificacion FROM Material WHERE codigoIdentificacion LIKE ? ORDER BY codigoIdentificacion DESC LIMIT 1";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, prefijo + "%");
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String codigo = rs.getString("codigoIdentificacion");
                    numero = Integer.parseInt(codigo.substring(3)); // Extraer el número del código
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        
        return numero;
    }
}
