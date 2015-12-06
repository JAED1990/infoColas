-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Servidor: localhost:3306
-- Tiempo de generación: 06-12-2015 a las 14:57:23
-- Versión del servidor: 5.5.46-cll
-- Versión de PHP: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `dxtre_colas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `idcategoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idcategoria`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idcategoria`, `nombre`) VALUES
(1, 'ONPE'),
(2, 'Restaurante'),
(3, 'Banco'),
(4, 'Cine'),
(5, 'Universidad');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cola`
--

CREATE TABLE IF NOT EXISTS `cola` (
  `idcola` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `estado` char(1) DEFAULT NULL,
  `idlugar` int(11) DEFAULT NULL,
  `dni` char(8) DEFAULT NULL,
  PRIMARY KEY (`idcola`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=53 ;

--
-- Volcado de datos para la tabla `cola`
--

INSERT INTO `cola` (`idcola`, `fecha`, `hora`, `estado`, `idlugar`, `dni`) VALUES
(1, '2015-12-05 00:00:00', '17:16:59', 'B', 1, '71574271'),
(2, '2015-12-06 00:00:00', '04:23:31', 'A', 1, '47697061'),
(3, '2015-12-06 00:00:00', '04:24:49', 'B', 1, '47697062'),
(4, '2015-12-06 00:00:00', '04:25:37', 'M', 1, '71574272'),
(5, '2015-12-06 00:00:00', '04:26:16', 'B', 1, '47941221'),
(6, '2015-12-06 05:54:05', '05:54:05', 'B', 1, '71574271'),
(7, '2015-12-06 05:56:37', '05:56:37', 'B', 2, '71574271'),
(8, '2015-12-06 08:30:16', '08:30:16', 'B', 2, '71574271'),
(9, '2015-12-06 08:30:37', '08:30:37', 'M', 4, '71574271'),
(10, '2015-12-06 08:30:43', '08:30:43', 'M', 5, '71574271'),
(11, '2015-12-06 08:30:52', '08:30:52', 'A', 6, '71574271'),
(12, '2015-12-06 08:32:48', '08:32:48', 'M', 2, '71574271'),
(13, '2015-12-06 09:21:15', '09:21:15', 'B', 2, '71574271'),
(14, '2015-12-06 09:21:36', '09:21:36', 'M', 1, '71574271'),
(15, '2015-12-06 09:24:55', '09:24:55', 'M', 1, '71574271'),
(16, '2015-12-06 09:31:00', '09:31:00', 'B', 1, '47941221'),
(17, '2015-12-06 09:32:13', '09:32:13', 'A', 1, '47941221'),
(18, '2015-12-06 09:32:54', '09:32:54', 'B', 1, '47941221'),
(19, '2015-12-06 09:33:21', '09:33:21', 'M', 3, '47941221'),
(20, '2015-12-06 09:33:37', '09:33:37', 'M', 1, '47941221'),
(21, '2015-12-06 09:33:38', '09:33:38', 'B', 6, '47941221'),
(22, '2015-12-06 09:36:52', '09:36:52', 'B', 1, '47941221'),
(23, '2015-12-06 10:35:52', '10:35:52', 'B', 7, '71574272'),
(24, '2015-12-06 11:23:25', '11:23:25', 'A', 4, '47941221'),
(25, '2015-12-06 11:23:32', '11:23:32', 'B', 4, '47941221'),
(26, '2015-12-06 11:23:44', '11:23:44', 'M', 4, '47941221'),
(27, '2015-12-06 11:23:47', '11:23:47', 'A', 4, '47941221'),
(28, '2015-12-06 11:23:50', '11:23:50', 'B', 4, '47941221'),
(29, '2015-12-06 11:23:51', '11:23:51', 'M', 4, '47941221'),
(30, '2015-12-06 11:23:55', '11:23:55', 'M', 4, '47941221'),
(31, '2015-12-06 11:23:59', '11:23:59', 'M', 4, '47941221'),
(32, '2015-12-06 11:24:04', '11:24:04', 'A', 4, '47941221'),
(33, '2015-12-06 11:24:07', '11:24:07', 'M', 4, '47941221'),
(34, '2015-12-06 11:24:08', '11:24:08', 'B', 4, '47941221'),
(35, '2015-12-06 11:24:17', '11:24:17', 'A', 4, '47941221'),
(36, '2015-12-06 11:25:19', '11:25:19', 'B', 5, '47941221'),
(37, '2015-12-06 11:25:29', '11:25:29', 'M', 5, '47941221'),
(38, '2015-12-06 11:25:37', '11:25:37', 'B', 5, '47941221'),
(39, '2015-12-06 11:31:05', '11:31:05', 'M', 5, '47941221'),
(40, '2015-12-06 11:52:07', '11:52:07', 'A', 5, '47941221'),
(41, '2015-12-06 13:14:27', '13:14:27', 'A', 2, '71574272'),
(42, '2015-12-06 13:14:31', '13:14:31', 'B', 2, '71574272'),
(43, '2015-12-06 13:16:58', '13:16:58', 'M', 9, '71574272'),
(44, '2015-12-06 13:17:32', '13:17:32', 'A', 9, '71574272'),
(45, '2015-12-06 13:17:34', '13:17:34', 'A', 9, '71574272'),
(46, '2015-12-06 13:17:48', '13:17:48', 'B', 10, '71574272'),
(47, '2015-12-06 13:26:18', '13:26:18', 'A', 9, '71574272'),
(48, '2015-12-06 13:26:47', '13:26:47', 'A', 8, '71574272'),
(49, '2015-12-06 13:26:59', '13:26:59', 'B', 8, '71574272'),
(50, '2015-12-06 13:27:36', '13:27:36', 'A', 2, '71574272'),
(51, '2015-12-06 13:27:45', '13:27:45', 'B', 2, '71574272'),
(52, '2015-12-06 13:27:56', '13:27:56', 'M', 3, '71574272');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar`
--

CREATE TABLE IF NOT EXISTS `lugar` (
  `idlugar` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `imagen` varchar(100) DEFAULT '/imagenes/prueba.jpg',
  `latitud` varchar(30) DEFAULT NULL,
  `longitud` varchar(30) DEFAULT NULL,
  `region` varchar(30) DEFAULT 'Lambayeque',
  `estado` char(1) DEFAULT 'P',
  `dni` char(8) DEFAULT NULL,
  `idcategoria` int(11) DEFAULT NULL,
  PRIMARY KEY (`idlugar`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Volcado de datos para la tabla `lugar`
--

INSERT INTO `lugar` (`idlugar`, `nombre`, `imagen`, `latitud`, `longitud`, `region`, `estado`, `dni`, `idcategoria`) VALUES
(1, 'Elecciones ', '/imagenes/onpe.png', '-6.775857', '-79.850174', 'Lambayeque', 'A', '47697061', 1),
(2, 'Marakos 490', '/imagenes/marakos.jpg', '-6.774201', '-79.849368', 'Lambayeque', 'A', '71574272', 2),
(3, 'D Pulpa', '/imagenes/dpulpa.jpg', '-6.781947', ' -79.841127', 'Lambayeque', 'A', '71574272', 2),
(4, 'El Warike', '/imagenes/warike.jpg', '-6.782426', '-79.841428', 'Lambayeque', 'A', '71574272', 2),
(5, 'Itadakimasu', '/imagenes/itadakimasu.jpg', '-6.778918', '-79.836943', 'Lambayeque', 'A', '71574272', 2),
(6, 'El Diez', '/imagenes/diez.jpg', '-6.782325', '-79.842163', 'Lambayeque', 'A', '71574272', 2),
(7, 'Fiesta Gourmet', '/imagenes/fiesta.jpg', '-6.768713', '-79.866572', 'Lambayeque', 'A', '71574272', 2),
(8, 'Caja Piura', '/imagenes/cajaPiura.jpg', '-6.772177', '-79.838583', 'Lambayeque', 'A', '47941221', 3),
(9, 'BCP de Balta', '/imagenes/Bcp.jpg', '-6.772559', '-79.838188', 'Lambayeque', 'A', '47941221', 3),
(10, 'ScotiaBank', '/imagenes/scotiabank.jpg', '-6.771500', '-79.839424', 'Lambayeque', 'A', '47941221', 3),
(11, 'Banco Falabella', '/imagenes/falabella.jpg', '-6.777060', '-79.835951', 'Lambayeque', 'A', '47941221', 3),
(12, 'Cine Planet', '/imagenes/cineplanet.jpg', '-6.778328', '-79.832229', 'Lambayeque', 'A', '47941221', 4),
(13, 'Usat', '/imagenes/usat.jpg', '-6.760190', '-79.863214', 'Lambayeque', 'A', '47941221', 5),
(14, 'Sipan', '/imagenes/sipan.jpg', '-6.794871', '-79.885257', 'Lambayeque', 'A', '47941221', 5),
(15, 'Cesar Vallejo', '/imagenes/vallejo.jpg', '-6.786699', '-79.879725', 'Lambayeque', 'A', '47941221', 5),
(16, 'UNPRG', '/imagenes/pedro.jpg', '-6.707282', '-79.904545', 'Lambayeque', 'A', '47941221', 5),
(18, 'prueba 2', '/imagenes/prueba.jpg', '-6.778162575941045', '-79.84298508614302', 'Lambayeque', 'P', '71574272', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `onpe`
--

CREATE TABLE IF NOT EXISTS `onpe` (
  `dni` char(8) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `num_mesa` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `onpe`
--

INSERT INTO `onpe` (`dni`, `nombres`, `apellidos`, `num_mesa`) VALUES
('47697061', 'Juan Antonio', 'Salazar Ramirez', '2322'),
('47697062', 'William Ivan', 'Salazar Ramirez', '232332'),
('47941221', 'Lucia Maria', 'Lopez Gonzales', '324434'),
('71574271', 'Luis ALberto', 'Falero Otiniano', '12323'),
('71574272', 'Mariano Luis', 'Perez ALbujar', '2223');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `dni` char(8) NOT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `clave` char(32) DEFAULT NULL,
  `estado` char(1) DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`dni`, `correo`, `clave`, `estado`) VALUES
('12345678', 'admin@gmail.com', '202cb962ac59075b964b07152d234b70', 'A'),
('47697061', 'srjuan54@gmail.com', 'd41d8cd98f00b204e9800998ecf8427e', 'A'),
('47697062', 'juahs@gmail.com', '202cb962ac59075b964b07152d234b70', 'A'),
('47941221', 'alvaro.torres.is@outlook.com', '202cb962ac59075b964b07152d234b70', 'A'),
('71574271', 'lufao1427@gmail.com', '202cb962ac59075b964b07152d234b70', 'A'),
('71574272', 'leyla14@gmail.com', '202cb962ac59075b964b07152d234b70', 'A');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
