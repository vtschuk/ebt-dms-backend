package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "userentity")
data class UserEntity(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(name = "first_name")
    var  firstName: String,
    @Column(name = "last_name")
    var  lastName: String,
    @Column(name="email")
    var email: String,
    @Column(name = "username")
    var username2: String,
    @Column(name = "password")
    var password2: String,
    var role: String,

    ) : UserDetails {

    @OneToMany(mappedBy = "userEntity")
    val tokens: List<JWToken>? = null

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role))
    }

    override fun getPassword(): String? {
        return password2
    }

    override fun getUsername(): String? {
        return username2
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
