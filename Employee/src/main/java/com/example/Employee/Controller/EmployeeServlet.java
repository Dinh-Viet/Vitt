package com.example.Employee.Controller;


import com.example.Employee.Entity.Employee;
import com.example.Employee.Model.EmployeeDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new EmployeeDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/add":
                    addEmployee(req, resp);
                    break;
                case "/delete":
                    deleteEmployee(req, resp);
                    break;
                default:
                    getAllEmployees(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllEmployees(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Employee> list = dao.getAllEmployees();
        req.setAttribute("listEmployee", list);
        RequestDispatcher dispatcher = req.getRequestDispatcher("employee.jsp");
        dispatcher.forward(req, resp);
    }

    private void addEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String cmnd = req.getParameter("cmnd");
        String name = req.getParameter("name");
        String bird = req.getParameter("bird");
        String address = req.getParameter("address");
        String position = req.getParameter("position");
        String work_room = req.getParameter("work_room");

        // Debugging statements
        System.out.println("CMND: " + cmnd);
        System.out.println("Name: " + name);
        System.out.println("Birthday: " + bird);
        System.out.println("Address: " + address);
        System.out.println("Position: " + position);
        System.out.println("Work Room: " + work_room);

        Employee employee = new Employee(cmnd, name, bird, address, position, work_room);
        dao.addEmployee(employee);

        // Chuyển hướng để cập nhật danh sách nhân viên
        resp.sendRedirect(req.getContextPath() + "/");
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("add-employee.jsp");
        dispatcher.forward(req, resp);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        dao.deleteEmployee();
        // Chuyển hướng để cập nhật danh sách nhân viên
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
