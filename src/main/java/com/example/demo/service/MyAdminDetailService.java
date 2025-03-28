package com.example.demo.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyAdminDetailService implements UserDetailsService {

    @Autowired
    AdminMapper adminService;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        QueryWrapper<Admin> queryWrapper =
                new QueryWrapper<Admin>().eq("username", adminName);
        Admin admin = adminService.selectOne(queryWrapper);
        UserDetails userDetails = User.withUsername(admin.getUsername()).password(admin.getPassword()).roles("ADMIN").build();
        return userDetails;
    }
}
