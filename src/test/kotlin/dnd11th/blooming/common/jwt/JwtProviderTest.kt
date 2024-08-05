package dnd11th.blooming.common.jwt

import dnd11th.blooming.domain.entity.user.UserClaims
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import javax.crypto.SecretKey

class JwtProviderTest : DescribeSpec({

    val userId = 1L
    val email = "blooming@naver.com"
    val nickname = "블루밍"
    val accessSecret = "coffeecoffeecoffeecoffeecoffeecoffeecoffeevcoffeecoffeecoffeecoffeecoffeecoffee"
    val refreshSecret = "coffeecoffeecoffeecoffeecoffeecoffeecoffeevcoffeecoffeecoffeecoffeecoffeecoffee"
    val jwtProperties =
        JwtProperties(
            access = JwtProperties.TokenProperties(accessSecret, 3600000L),
            refresh = JwtProperties.TokenProperties(refreshSecret, 7200000L),
        )

    val jwtProvider = JwtProvider(jwtProperties)

    describe("generateToken") {

        context("userId, email이 전달되면") {

            val secretKey: SecretKey = Keys.hmacShaKeyFor(accessSecret.toByteArray())

            it("토큰을 생성한다") {
                val token = jwtProvider.generateAccessToken(userId, email, nickname)
                val claims: Claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
                val claimId = claims["id"]
                val claimEmail = claims["email"]
                val claimNickname = claims["nickname"]

                claimId shouldBe userId
                claimEmail shouldBe email
                claimNickname shouldBe nickname
            }
        }
    }

    describe("verifyToken") {

        context("userId, email이 전달되면") {
            val token = jwtProvider.generateAccessToken(userId, email, nickname)
            it("토큰을 생성한다") {
                val userClaims: UserClaims = jwtProvider.resolveAccessToken(token)
                val claimId = userClaims.id
                val claimEmail = userClaims.email
                val claimNickname = userClaims.nickname

                claimId shouldBe userId
                claimEmail shouldBe email
                claimNickname shouldBe nickname
            }
        }
    }
})
