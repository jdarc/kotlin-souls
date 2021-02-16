class Particle(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) {
    var position = Vector3(x, y, z)
    var velocity = Vector3.ZERO
    var acceleration = Vector3.ZERO

    fun clearForces() {
        acceleration = Vector3(0.0, 0.0, 0.0)
    }

    fun accelerate(force: Vector3) {
        acceleration += force
    }

    fun integrate(dt: Double) {
        velocity += acceleration * dt
        position += velocity * dt
    }

    fun dampen(x: Double, y: Double, z: Double) {
        velocity = Vector3(velocity.x * x, velocity.y * y, velocity.z * z)
    }
}
