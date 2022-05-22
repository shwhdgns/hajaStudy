package com.go.study.github.service;

import org.junit.Before;
import org.junit.Test;
import org.kohsuke.github.*;

import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GitHubCommitServiceTest {

        GitHub github;
        String token = "ghp_";
        String githubId = "shwhdgns";
        String repoName = "hajaStudy";

        @Before
        public void 깃허브_토큰_기반_접근_확인() throws IOException {
            github = new GitHubBuilder().withOAuthToken(token).build();
            github.checkApiUrlValidity();
        }

        @Test
        public void 깃허브_저장소_가져오기() throws IOException {
            GHRepository repository = github.getRepository(githubId + "/" + repoName);
        }

        public void 깃허브_브랜치_가져오기() throws IOException {
            Map<String, GHBranch> gbBranchMap = github.getRepository(githubId + "/" + repoName).getBranches();
            gbBranchMap.forEach((item, branchInfo) -> System.out.println(branchInfo));
        }

        @Test
        public void 깃허브_커밋_내역_조회() throws IOException {
            // given
            PagedIterable<GHCommit> commitPagedIterable = github.getRepository(githubId + "/" + repoName).listCommits();

            // when
            commitPagedIterable.forEach(item -> {
                try {
                    GHCommit.ShortInfo shortInfo = item.getCommitShortInfo();
                    System.out.println(shortInfo.getAuthor());
                    System.out.println(shortInfo.getCommitDate());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // then
            assertThat(commitPagedIterable.toList().size()).isGreaterThan(0);
        }
    }
