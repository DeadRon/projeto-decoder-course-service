package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    /*
    este comando é usado para checar se existe um registro na tabela tb_courses_users que associa um determinado
    curso (identificado por courseId) a um determinado usuário (identificado por userId), retornando true
    se o registro existir e false se não existir.
    */
    @Query(
            value = "SELECT CASE WHEN COUNT(tcu) > 0 THEN true ELSE false END FROM tb_courses_users tcu " +
                    "WHERE tcu.course_id= :courseId " +
                    "AND tcu.user_id= :userId",
            nativeQuery = true
    )
    boolean existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    //associa um estudante ha um curso
    @Modifying
    @Query(
            value = "INSERT INTO tb_courses_users VALUES (:courseId, :userId);",
            nativeQuery = true
    )
    void saveCourseUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    //deleta ou cancela a associação de um usuário em um curso
    @Modifying
    @Query(
            value = "DELETE FROM tb_courses_users WHERE course_id= :courseId", nativeQuery = true
    )
    void deleteCourseUserByCourse(@Param("courseId") UUID courseId);

    //deleta um usuário
    @Modifying
    @Query(
            value = "DELETE FROM tb_courses_users WHERE user_id= :userId", nativeQuery = true
    )
    void deleteCourseUserByUser(@Param("userId") UUID userId);

}
