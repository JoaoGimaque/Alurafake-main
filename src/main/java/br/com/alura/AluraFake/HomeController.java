package br.com.alura.AluraFake;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return """
                
                     <title>Plataforma Alura</title>
                     <style>
                       body {
                         margin: 0;
                         font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                         background-color: #f5f5f5;
                         color: #003E6B;
                       }
                
                       header {
                         background-color: #003E6B;
                         color: white;
                         padding: 20px 0;
                         text-align: center;
                         box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                       }
                
                       header h1 {
                         margin: 0;
                         font-size: 2.5em;
                       }
                
                       main {
                         padding: 40px;
                         text-align: center;
                       }
                
                       main h2 {
                         color: #F68A1E;
                         margin-bottom: 30px;
                       }
                
                       ul {
                         list-style: none;
                         padding: 0;
                         display: flex;
                         flex-direction: column;
                         align-items: center;
                         gap: 20px;
                       }
                
                       li {
                         position: relative;
                         z-index: 1;
                       }
                
                       a {
                         color: white;
                         background-color: #F68A1E;
                         text-decoration: none;
                         font-weight: bold;
                         font-size: 1.2em;
                         padding: 15px 30px;
                         border-radius: 8px;
                         box-shadow: 0 8px 20px rgba(0, 62, 107, 0.3);
                         transition: transform 0.3s, box-shadow 0.3s;
                         display: inline-block;
                       }
                
                       a:hover {
                         background-color: #003E6B;
                         transform: translateY(-5px);
                         box-shadow: 0 12px 25px rgba(0, 62, 107, 0.4);
                       }
                
                       footer {
                         background-color: #003E6B;
                         color: white;
                         text-align: center;
                         padding: 15px 0;
                         position: fixed;
                         bottom: 0;
                         width: 100%;
                       }
                     </style>
                   </head>
                   <body>
                
                     <header>
                       <h1>Bem-vindo à Plataforma Alura</h1>
                     </header>
                
                     <main>
                       <h2>Escolha uma opção abaixo</h2>
                       <ul>
                         <li><a href="/course/all">Lista de Cursos</a></li>
                         <li><a href="/user/all">Lista de Usuários</a></li>
                         <li><a href="/task/all">lista de tarefas</a></li>
                       </ul>
                     </main>
                
                     <footer>
                       &copy; 2025 Alura. Todos os direitos reservados.
                     </footer>
                                  
            """;
    }
}