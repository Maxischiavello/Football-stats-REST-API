package dev.maxischiavello.football_stats.match;

import dev.maxischiavello.football_stats.result.Result;
import dev.maxischiavello.football_stats.substitution.Substitution;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "result_id", referencedColumnName = "id")
    private Result result = new Result();

    @Temporal(TemporalType.DATE)
    @Column(name = "match_date")
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "match_id")
    private List<Substitution> substitutions = new ArrayList<>();

    public Match() {
    }

    public Match(Integer id, Result result, Date date, List<Substitution> substitutions) {
        this.id = id;
        this.result = result;
        this.date = date;
        this.substitutions = substitutions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Substitution> getSubstitutions() {
        return substitutions;
    }

    public void setSubstitutions(List<Substitution> substitutions) {
        this.substitutions = substitutions;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", result=" + result +
                ", date=" + date +
                ", substitutions=" + substitutions +
                '}';
    }
}
