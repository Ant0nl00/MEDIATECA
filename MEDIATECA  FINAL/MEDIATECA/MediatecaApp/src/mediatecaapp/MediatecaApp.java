package mediatecaapp;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MediatecaApp {
    public static void main(String[] args) {
        int opcion;
        do {
            String menu = "Seleccione una opción:\n"
                    + "1. Agregar material\n"
                    + "2. Modificar material\n"
                    + "3. Listar materiales\n"
                    + "4. Borrar material\n"
                    + "5. Buscar material\n"
                    + "6. Salir";
            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1:
                    agregarMaterial();
                    break;
                case 2:
                    modificarMaterial();
                    break;
                case 3:
                    listarMateriales();
                    break;
                case 4:
                    borrarMaterial();
                    break;
                case 5:
                    buscarMaterial();
                    break;
                case 6:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    private static void agregarMaterial() {
        String[] tipos = {"LIBRO", "REVISTA", "CD", "DVD"};
        String tipoMaterial = (String) JOptionPane.showInputDialog(null, 
                "Seleccione el tipo de material a agregar:", 
                "Agregar Material", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                tipos, 
                tipos[0]);

        String titulo = JOptionPane.showInputDialog("Ingrese el título del material:");
        int unidadesDisponibles = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de unidades disponibles:"));
        String codigoIdentificacion = GeneradorCodigo.generarCodigo(tipoMaterial);
        
        try (Connection connection = DatabaseConnection.conectar()) {
            String sqlInsertMaterial = "INSERT INTO Material (codigoIdentificacion, titulo, unidadesDisponibles) VALUES (?, ?, ?)";
            try (PreparedStatement pstmtMaterial = connection.prepareStatement(sqlInsertMaterial)) {
                pstmtMaterial.setString(1, codigoIdentificacion);
                pstmtMaterial.setString(2, titulo);
                pstmtMaterial.setInt(3, unidadesDisponibles);
                pstmtMaterial.executeUpdate();
            }

            switch (tipoMaterial) {
                case "LIBRO":
                    agregarLibro(connection, codigoIdentificacion);
                    break;
                case "REVISTA":
                    agregarRevista(connection, codigoIdentificacion);
                    break;
                case "CD":
                    agregarCD(connection, codigoIdentificacion);
                    break;
                case "DVD":
                    agregarDVD(connection, codigoIdentificacion);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Tipo de material no reconocido.");
            }

            JOptionPane.showMessageDialog(null, "Material agregado exitosamente. Código: " + codigoIdentificacion);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar material: " + e.getMessage());
        }
    }
    
    private static void agregarLibro(Connection connection, String codigoIdentificacion) throws SQLException {
        String autor = JOptionPane.showInputDialog("Ingrese el autor del libro:");
        int numeroPaginas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de páginas:"));
        String editorial = JOptionPane.showInputDialog("Ingrese la editorial:");
        String isbn = JOptionPane.showInputDialog("Ingrese el ISBN:");
        int anioPublicacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el año de publicación:"));

        String sqlInsertLibro = "INSERT INTO Libro (codigoIdentificacion, autor, numeroPaginas, editorial, ISBN, anioPublicacion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmtLibro = connection.prepareStatement(sqlInsertLibro)) {
            pstmtLibro.setString(1, codigoIdentificacion);
            pstmtLibro.setString(2, autor);
            pstmtLibro.setInt(3, numeroPaginas);
            pstmtLibro.setString(4, editorial);
            pstmtLibro.setString(5, isbn);
            pstmtLibro.setInt(6, anioPublicacion);
            pstmtLibro.executeUpdate();
        }
    }

    private static void agregarRevista(Connection connection, String codigoIdentificacion) throws SQLException {
        String editorial = JOptionPane.showInputDialog("Ingrese la editorial de la revista:");
        String periodicidad = JOptionPane.showInputDialog("Ingrese la periodicidad:");
        String fechaPublicacion = JOptionPane.showInputDialog("Ingrese la fecha de publicación (YYYY-MM-DD):");

        String sqlInsertRevista = "INSERT INTO Revista (codigoIdentificacion, editorial, periodicidad, fechaPublicacion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmtRevista = connection.prepareStatement(sqlInsertRevista)) {
            pstmtRevista.setString(1, codigoIdentificacion);
            pstmtRevista.setString(2, editorial);
            pstmtRevista.setString(3, periodicidad);
            pstmtRevista.setDate(4, java.sql.Date.valueOf(fechaPublicacion));
            pstmtRevista.executeUpdate();
        }
    }

    private static void agregarCD(Connection connection, String codigoIdentificacion) throws SQLException {
        String artista = JOptionPane.showInputDialog("Ingrese el artista:");
        String genero = JOptionPane.showInputDialog("Ingrese el género:");
        String duracion = JOptionPane.showInputDialog("Ingrese la duración (HH:MM:SS):");
        int numeroCanciones = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de canciones:"));

        String sqlInsertCD = "INSERT INTO CDDeAudio (codigoIdentificacion, artista, genero, duracion, numeroCanciones) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmtCD = connection.prepareStatement(sqlInsertCD)) {
            pstmtCD.setString(1, codigoIdentificacion);
            pstmtCD.setString(2, artista);
            pstmtCD.setString(3, genero);
            pstmtCD.setTime(4, java.sql.Time.valueOf(duracion));
            pstmtCD.setInt(5, numeroCanciones);
            pstmtCD.executeUpdate();
        }
    }

    private static void agregarDVD(Connection connection, String codigoIdentificacion) throws SQLException {
        String director = JOptionPane.showInputDialog("Ingrese el director:");
        String duracion = JOptionPane.showInputDialog("Ingrese la duración (HH:MM:SS):");
        String genero = JOptionPane.showInputDialog("Ingrese el género:");

        String sqlInsertDVD = "INSERT INTO DVD (codigoIdentificacion, director, duracion, genero) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmtDVD = connection.prepareStatement(sqlInsertDVD)) {
            pstmtDVD.setString(1, codigoIdentificacion);
            pstmtDVD.setString(2, director);
            pstmtDVD.setTime(3, java.sql.Time.valueOf(duracion));
            pstmtDVD.setString(4, genero);
            pstmtDVD.executeUpdate();
        }
    }

    private static void modificarMaterial() {
        String codigoIdentificacion = JOptionPane.showInputDialog("Ingrese el código de identificación del material a modificar:");
        
        try (Connection connection = DatabaseConnection.conectar()) {
            String sqlSelect = "SELECT * FROM Material WHERE codigoIdentificacion = ?";
            try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
                pstmtSelect.setString(1, codigoIdentificacion);
                ResultSet rs = pstmtSelect.executeQuery();

                if (rs.next()) {
                    String nuevoTitulo = JOptionPane.showInputDialog("Ingrese el nuevo título:", rs.getString("titulo"));
                    int nuevasUnidadesDisponibles = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo número de unidades disponibles:", rs.getInt("unidadesDisponibles")));
                    
                    String sqlUpdateMaterial = "UPDATE Material SET titulo = ?, unidadesDisponibles = ? WHERE codigoIdentificacion = ?";
                    try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdateMaterial)) {
                        pstmtUpdate.setString(1, nuevoTitulo);
                        pstmtUpdate.setInt(2, nuevasUnidadesDisponibles);
                        pstmtUpdate.setString(3, codigoIdentificacion);
                        pstmtUpdate.executeUpdate();
                    }

                    String tipoMaterial = JOptionPane.showInputDialog("Ingrese el tipo de material (LIBRO, REVISTA, CD, DVD):").toUpperCase();
                    switch (tipoMaterial) {
                        case "LIBRO":
                            modificarLibro(connection, codigoIdentificacion);
                            break;
                        case "REVISTA":
                            modificarRevista(connection, codigoIdentificacion);
                            break;
                        case "CD":
                            modificarCD(connection, codigoIdentificacion);
                            break;
                        case "DVD":
                            modificarDVD(connection, codigoIdentificacion);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Tipo de material no reconocido.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Material no encontrado.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar material: " + e.getMessage());
        }
    }

    private static void modificarLibro(Connection connection, String codigoIdentificacion) throws SQLException {
        String sqlSelect = "SELECT * FROM Libro WHERE codigoIdentificacion = ?";
        try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
            pstmtSelect.setString(1, codigoIdentificacion);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                String autor = JOptionPane.showInputDialog("Ingrese el nuevo autor:", rs.getString("autor"));
                int numeroPaginas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo número de páginas:", rs.getInt("numeroPaginas")));
                String editorial = JOptionPane.showInputDialog("Ingrese la nueva editorial:", rs.getString("editorial"));
                String isbn = JOptionPane.showInputDialog("Ingrese el nuevo ISBN:", rs.getString("ISBN"));
                int anioPublicacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo año de publicación:", rs.getInt("anioPublicacion")));

                String sqlUpdateLibro = "UPDATE Libro SET autor = ?, numeroPaginas = ?, editorial = ?, ISBN = ?, anioPublicacion = ? WHERE codigoIdentificacion = ?";
                try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdateLibro)) {
                    pstmtUpdate.setString(1, autor);
                    pstmtUpdate.setInt(2, numeroPaginas);
                    pstmtUpdate.setString(3, editorial);
                    pstmtUpdate.setString(4, isbn);
                    pstmtUpdate.setInt(5, anioPublicacion);
                    pstmtUpdate.setString(6, codigoIdentificacion);
                    pstmtUpdate.executeUpdate();
                }
            }
        }
    }

    private static void modificarRevista(Connection connection, String codigoIdentificacion) throws SQLException {
        String sqlSelect = "SELECT * FROM Revista WHERE codigoIdentificacion = ?";
        try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
            pstmtSelect.setString(1, codigoIdentificacion);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                String editorial = JOptionPane.showInputDialog("Ingrese la nueva editorial:", rs.getString("editorial"));
                String periodicidad = JOptionPane.showInputDialog("Ingrese la nueva periodicidad:", rs.getString("periodicidad"));
                String fechaPublicacion = JOptionPane.showInputDialog("Ingrese la nueva fecha de publicación (YYYY-MM-DD):", rs.getDate("fechaPublicacion"));

                String sqlUpdateRevista = "UPDATE Revista SET editorial = ?, periodicidad = ?, fechaPublicacion = ? WHERE codigoIdentificacion = ?";
                try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdateRevista)) {
                    pstmtUpdate.setString(1, editorial);
                    pstmtUpdate.setString(2, periodicidad);
                    pstmtUpdate.setDate(3, java.sql.Date.valueOf(fechaPublicacion));
                    pstmtUpdate.setString(4, codigoIdentificacion);
                    pstmtUpdate.executeUpdate();
                }
            }
        }
    }

    private static void modificarCD(Connection connection, String codigoIdentificacion) throws SQLException {
        String sqlSelect = "SELECT * FROM CDDeAudio WHERE codigoIdentificacion = ?";
        try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
            pstmtSelect.setString(1, codigoIdentificacion);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                String artista = JOptionPane.showInputDialog("Ingrese el nuevo artista:", rs.getString("artista"));
                String genero = JOptionPane.showInputDialog("Ingrese el nuevo género:", rs.getString("genero"));
                String duracion = JOptionPane.showInputDialog("Ingrese la nueva duración (HH:MM:SS):", rs.getTime("duracion"));
                int numeroCanciones = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo número de canciones:", rs.getInt("numeroCanciones")));

                String sqlUpdateCD = "UPDATE CDDeAudio SET artista = ?, genero = ?, duracion = ?, numeroCanciones = ? WHERE codigoIdentificacion = ?";
                try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdateCD)) {
                    pstmtUpdate.setString(1, artista);
                    pstmtUpdate.setString(2, genero);
                    pstmtUpdate.setTime(3, java.sql.Time.valueOf(duracion));
                    pstmtUpdate.setInt(4, numeroCanciones);
                    pstmtUpdate.setString(5, codigoIdentificacion);
                    pstmtUpdate.executeUpdate();
                }
            }
        }
    }

    private static void modificarDVD(Connection connection, String codigoIdentificacion) throws SQLException {
        String sqlSelect = "SELECT * FROM DVD WHERE codigoIdentificacion = ?";
        try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
            pstmtSelect.setString(1, codigoIdentificacion);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                String director = JOptionPane.showInputDialog("Ingrese el nuevo director:", rs.getString("director"));
                String duracion = JOptionPane.showInputDialog("Ingrese la nueva duración (HH:MM:SS):", rs.getTime("duracion"));
                String genero = JOptionPane.showInputDialog("Ingrese el nuevo género:", rs.getString("genero"));

                String sqlUpdateDVD = "UPDATE DVD SET director = ?, duracion = ?, genero = ? WHERE codigoIdentificacion = ?";
                try (PreparedStatement pstmtUpdate = connection.prepareStatement(sqlUpdateDVD)) {
                    pstmtUpdate.setString(1, director);
                    pstmtUpdate.setTime(2, java.sql.Time.valueOf(duracion));
                    pstmtUpdate.setString(3, genero);
                    pstmtUpdate.setString(4, codigoIdentificacion);
                    pstmtUpdate.executeUpdate();
                }
            }
        }
    }

    private static void listarMateriales() {
    StringBuilder materiales = new StringBuilder();

    try (Connection connection = DatabaseConnection.conectar()) {
        String sqlSelect = "SELECT * FROM Material";
        try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
            ResultSet rs = pstmtSelect.executeQuery();

            while (rs.next()) {
                materiales.append("Código: ").append(rs.getString("codigoIdentificacion"))
                          .append(", Título: ").append(rs.getString("titulo"))
                          .append(", Unidades Disponibles: ").append(rs.getInt("unidadesDisponibles"))
                          .append("\n");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al listar materiales: " + e.getMessage());
    }

    // Comprobar si el StringBuilder está vacío
    if (materiales.length() == 0) {
        JOptionPane.showMessageDialog(null, "No hay materiales que mostrar.", "Materiales Disponibles", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null, materiales.toString(), "Materiales Disponibles", JOptionPane.INFORMATION_MESSAGE);
    }
}

    private static void borrarMaterial() {
    String codigoIdentificacion = JOptionPane.showInputDialog("Ingrese el código de identificación del material a borrar:");
    
    try (Connection connection = DatabaseConnection.conectar()) {
        // Primero, eliminamos el material de las tablas secundarias
        String sqlDeleteLibro = "DELETE FROM Libro WHERE codigoIdentificacion = ?";
        String sqlDeleteRevista = "DELETE FROM Revista WHERE codigoIdentificacion = ?";
        String sqlDeleteCD = "DELETE FROM CDDeAudio WHERE codigoIdentificacion = ?";
        String sqlDeleteDVD = "DELETE FROM DVD WHERE codigoIdentificacion = ?";
        
        // Se ejecutan las eliminaciones en las tablas secundarias
        try (PreparedStatement pstmtLibro = connection.prepareStatement(sqlDeleteLibro);
             PreparedStatement pstmtRevista = connection.prepareStatement(sqlDeleteRevista);
             PreparedStatement pstmtCD = connection.prepareStatement(sqlDeleteCD);
             PreparedStatement pstmtDVD = connection.prepareStatement(sqlDeleteDVD)) {
             
            pstmtLibro.setString(1, codigoIdentificacion);
            pstmtLibro.executeUpdate();
            
            pstmtRevista.setString(1, codigoIdentificacion);
            pstmtRevista.executeUpdate();
            
            pstmtCD.setString(1, codigoIdentificacion);
            pstmtCD.executeUpdate();
            
            pstmtDVD.setString(1, codigoIdentificacion);
            pstmtDVD.executeUpdate();
        }

        // Ahora eliminamos el material de la tabla Material
        String sqlDeleteMaterial = "DELETE FROM Material WHERE codigoIdentificacion = ?";
        try (PreparedStatement pstmtMaterial = connection.prepareStatement(sqlDeleteMaterial)) {
            pstmtMaterial.setString(1, codigoIdentificacion);
            int rowsAffected = pstmtMaterial.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Material borrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el material con el código proporcionado.");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al borrar material: " + e.getMessage());
    }
}


    private static void buscarMaterial() {
        String codigoIdentificacion = JOptionPane.showInputDialog("Ingrese el código de identificación del material a buscar:");

        try (Connection connection = DatabaseConnection.conectar()) {
            String sqlSelect = "SELECT * FROM Material WHERE codigoIdentificacion = ?";
            try (PreparedStatement pstmtSelect = connection.prepareStatement(sqlSelect)) {
                pstmtSelect.setString(1, codigoIdentificacion);
                ResultSet rs = pstmtSelect.executeQuery();

                if (rs.next()) {
                    String materialInfo = "Código: " + rs.getString("codigoIdentificacion") +
                            ", Título: " + rs.getString("titulo") +
                            ", Unidades Disponibles: " + rs.getInt("unidadesDisponibles");
                    JOptionPane.showMessageDialog(null, materialInfo, "Material Encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Material no encontrado.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar material: " + e.getMessage());
        }
    }
}
