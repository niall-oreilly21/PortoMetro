package com.metroporto;


import com.metroporto.cards.GreyCard;
import com.metroporto.enums.CardAccessType;
import com.metroporto.exceptions.DaoException;
import org.flywaydb.core.Flyway;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class App
{
    public static void main(String[] args)
    {
        GreyCard card = new GreyCard(1, CardAccessType.ALL_ZONES, 12, LocalDateTime.of(2025, 12,12,12,12,12));

        System.out.println(card.isActive());
    }
}











